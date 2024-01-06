package io.github.sinisterquests.api.base;

import io.github.sinisterquests.exceptions.UnknownQuestException;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface QuestManager {
	
	void registerQuest(Quest quest);
	
	void unregisterQuest(String questName);
	
	Optional<Quest> getQuest(String name);
	
	void setPlayerContainer(PlayerQuestContainer questsContainer);
	
	Optional<PlayerQuestContainer> getPlayerQuests(UUID uuid);
	
	CompletableFuture<Void> assignQuests(UUID uuid, Quest... quests) throws UnknownQuestException;
	
	default boolean isQuestRegistered(String name) {
		return getQuest(name).isPresent();
	}
	
	void progressPlayer(Player player, Quest quest);
	
	void unCachePlayer(UUID uniqueId);
}
