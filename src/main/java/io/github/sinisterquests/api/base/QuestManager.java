package io.github.sinisterquests.base;

import io.github.sinisterquests.exceptions.UnknownQuestException;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface QuestManager {
	
	void registerQuest(Quest quest);
	
	void unregisterQuest(String questName);

	Optional<Quest> getQuest(String name);
	
	Optional<PlayerQuestContainer> getPlayerQuests(UUID uuid);
	
	void assignQuests(UUID uuid, Quest... quests) throws UnknownQuestException;
	
	
	default boolean isQuestRegistered(String name) {
		return getQuest(name).isPresent();
	}
	
	void progressPlayer(Player player, Quest quest);
}
