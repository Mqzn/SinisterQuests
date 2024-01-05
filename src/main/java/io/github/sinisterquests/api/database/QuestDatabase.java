package io.github.sinisterquests.api.database;

import io.github.sinisterquests.api.base.PlayerQuestContainer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface QuestDatabase {
	
	Type type();
	
	void initialize();
	
	CompletableFuture<Void> givePlayerQuests(UUID uuid, PlayerQuestContainer container);
	
	CompletableFuture<PlayerQuestContainer> loadPlayerQuests(UUID uuid);
	
	CompletableFuture<Void> unloadAndSave(UUID uuid, PlayerQuestContainer container);
	
	enum Type {
		
		SQL,
		
		MONGO,
		
		YAML;
		
		
	}
}
