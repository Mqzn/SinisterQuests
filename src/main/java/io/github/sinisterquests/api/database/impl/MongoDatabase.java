package io.github.sinisterquests.api.database.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.github.sinisterquests.api.SinisterQuestAPIProvider;
import io.github.sinisterquests.api.base.PlayerQuest;
import io.github.sinisterquests.api.base.PlayerQuestContainer;
import io.github.sinisterquests.api.database.QuestDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class MongoDatabase implements QuestDatabase {
	
	public final static String QUESTS_COLLECTION = "quests", PLAYER_QUESTS_COLLECTION = "player-quests-progress";
	
	private com.mongodb.client.MongoDatabase database;
	
	public MongoDatabase() {
	
	}
	
	@Override
	public Type type() {
		return Type.MONGO;
	}
	
	@Override
	public void initialize() {
		var config = SinisterQuestAPIProvider.get().config();
		String user = config.getString("mongo.username");
		String password = config.getString("mongo.password");
		String host = config.getString("mongo.host");
		String db = config.getString("mongo.database");
		int port = config.getInt("mongo.port");
		assert db != null;
		
		MongoClient client = MongoClients.create("mongodb://" + user + ":" + password + "@" + host + ":" + port);
		database = client.getDatabase(db);
		
		database.createCollection("quests");
		database.createCollection("player-quests-progress");
	}
	
	
	@Override
	public CompletableFuture<Void> givePlayerQuests(UUID uuid, PlayerQuestContainer container) {
		MongoCollection<Document> all = database.getCollection(PLAYER_QUESTS_COLLECTION);
		
		return CompletableFuture.runAsync(() -> {
			List<Document> docs = new ArrayList<>();
			for (var quest : container) {
				docs.add(quest.asDoc());
			}
			all.insertMany(docs);
		});
	}
	
	@Override
	public CompletableFuture<PlayerQuestContainer> loadPlayerQuests(UUID uuid) {
		MongoCollection<Document> all = database.getCollection(PLAYER_QUESTS_COLLECTION);
		
		return CompletableFuture.supplyAsync(() -> {
			PlayerQuestContainer container = new PlayerQuestContainer(uuid);
			
			try (var cursor = all.find(Filters.eq("uuid", uuid.toString())).cursor()) {
				
				while (cursor.hasNext()) {
					
					Document nextDoc = cursor.next();
					PlayerQuest playerQuest = PlayerQuest.fromDoc(nextDoc);
					if (playerQuest == null) continue;
					container.setQuest(playerQuest.getQuest().getName(), playerQuest);
				}
			}
			
			return container;
		});
	}
	
	
	@Override
	public CompletableFuture<Void> unloadAndSave(UUID uuid, PlayerQuestContainer container) {
		MongoCollection<Document> all = database.getCollection(PLAYER_QUESTS_COLLECTION);
		
		List<Document> docs = new ArrayList<>();
		for (var quest : container) docs.add(quest.asDoc());
		
		return CompletableFuture.runAsync(() -> {
			for (Document doc : docs) {
				String name = doc.getString("name");
				all.replaceOne(Filters.and(Filters.eq("uuid", uuid.toString()),
					Filters.eq("name", name)), doc);
			}
		});
	}
}
