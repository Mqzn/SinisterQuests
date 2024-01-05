package io.github.sinisterquests.api.database.impl;

import io.github.sinisterquests.api.base.PlayerQuestContainer;
import io.github.sinisterquests.api.database.QuestDatabase;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class SQLDatabase implements QuestDatabase {
	
	//TODO implement using SQLava
	@Override
	public Type type() {
		return Type.SQL;
	}
	
	@Override
	public void initialize() {
	
	}
	
	@Override
	public CompletableFuture<Void> givePlayerQuests(UUID uuid, PlayerQuestContainer container) {
		return null;
	}
	
	@Override
	public CompletableFuture<PlayerQuestContainer> loadPlayerQuests(UUID uuid) {
		return null;
	}
	
	@Override
	public CompletableFuture<Void> unloadAndSave(UUID uuid, PlayerQuestContainer container) {
		return null;
	}
}
