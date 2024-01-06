package io.github.sinisterquests.api.base.impl;

import io.github.sinisterquests.SinisterQuests;
import io.github.sinisterquests.api.base.PlayerQuestContainer;
import io.github.sinisterquests.api.base.Quest;
import io.github.sinisterquests.api.base.QuestManager;
import io.github.sinisterquests.api.events.PlayerQuestCompletionEvent;
import io.github.sinisterquests.api.events.PlayerQuestLevelUpgradeEvent;
import io.github.sinisterquests.api.events.PlayerQuestProgressEvent;
import io.github.sinisterquests.exceptions.UnknownQuestException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class SimpleQuestManager implements QuestManager {
	
	private final SinisterQuests plugin;
	
	private final Map<String, Quest> quests = new HashMap<>();
	
	private final Map<UUID, PlayerQuestContainer> playerQuests = new HashMap<>();
	
	public SimpleQuestManager(SinisterQuests plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void registerQuest(Quest quest) {
		quests.put(quest.getName(), quest);
		Bukkit.getPluginManager().registerEvents(quest.getEvent(), plugin);
	}
	
	@Override
	public void unregisterQuest(String questName) {
		var quest = quests.get(questName);
		if (quest == null) return;
		
		HandlerList.unregisterAll(quest.getEvent());
		quests.remove(questName);
	}
	
	@Override
	public Optional<Quest> getQuest(String name) {
		return Optional.ofNullable(quests.get(name));
	}
	
	@Override
	public void setPlayerContainer(PlayerQuestContainer questsContainer) {
		this.playerQuests.put(questsContainer.getUuid(), questsContainer);
	}
	
	@Override
	public Optional<PlayerQuestContainer> getPlayerQuests(UUID uuid) {
		return Optional.ofNullable(playerQuests.get(uuid));
	}
	
	@Override
	public CompletableFuture<Void> assignQuests(UUID uuid, Quest... quests) throws UnknownQuestException {
		
		for (Quest q : quests) {
			if (!isQuestRegistered(q.getName())) {
				throw new UnknownQuestException(q);
			}
		}
		
		playerQuests.computeIfPresent(uuid, (id, oldQuests) -> {
			for (Quest q : quests)
				oldQuests.assignQuest(q);
			
			return oldQuests;
		});
		
		
		return plugin.getDatabase().givePlayerQuests(uuid, playerQuests.getOrDefault(uuid, null));
	}
	
	@Override
	public void progressPlayer(Player player, Quest quest) {
		
		playerQuests.computeIfPresent(player.getUniqueId(), (uuid, questsContainer) -> {
			var playerQuest = questsContainer.getPlayerQuest(quest);
			if (playerQuest.isEmpty()) return questsContainer;
			
			var playerQuestObj = playerQuest.get();
			playerQuestObj.getCurrentProgress().ifPresent((progress) -> {
				
				int oldCount = progress.getCount();
				progress.increment(); //just +1
				int newCount = oldCount + 1;
				
				Bukkit.getPluginManager().callEvent(new PlayerQuestProgressEvent(player, quest, oldCount, newCount));
				
				if (newCount == progress.getMaxCount()) {
					
					playerQuestObj.getQuest().reward(player, playerQuestObj.getCurrentLevel());
					
					if (playerQuestObj.hasNextLevel()) {
						
						int oldLvl = playerQuestObj.getCurrentLevel();
						playerQuestObj.incrementLevel();
						int nextLvl = oldLvl + 1;
						
						Bukkit.getPluginManager().callEvent(new PlayerQuestLevelUpgradeEvent(player, quest, oldLvl, nextLvl));
						
					} else {
						HandlerList.unregisterAll(quest.getEvent());
						Bukkit.getPluginManager().callEvent(new PlayerQuestCompletionEvent(player, quest));
					}
					
				}
			});
			questsContainer.updateQuest(playerQuestObj);
			return questsContainer;
		});
		
	}
	
	@Override
	public void unCachePlayer(UUID uniqueId) {
		playerQuests.remove(uniqueId);
	}
}
