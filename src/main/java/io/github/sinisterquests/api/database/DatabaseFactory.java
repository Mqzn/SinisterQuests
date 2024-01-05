package io.github.sinisterquests.api.database;

import io.github.sinisterquests.api.database.impl.MongoDatabase;
import io.github.sinisterquests.api.database.impl.SQLDatabase;
import io.github.sinisterquests.api.database.impl.YamlDatabase;
import io.github.sinisterquests.exceptions.InvalidDatabaseTypeException;

public final class DatabaseFactory {
	
	private static DatabaseFactory factory;
	
	private DatabaseFactory() {
	}
	
	public static DatabaseFactory getFactory() {
		if (factory == null) {
			factory = new DatabaseFactory();
		}
		return factory;
	}
	
	public QuestDatabase createDatabase(String type) throws InvalidDatabaseTypeException {
		
		QuestDatabase.Type databaseType = QuestDatabase.Type.valueOf(type.toUpperCase());
		return createDatabase(databaseType);
	}
	
	public QuestDatabase createDatabase(QuestDatabase.Type type) throws InvalidDatabaseTypeException {
		switch (type) {
			case SQL -> new SQLDatabase();
			case MONGO -> new MongoDatabase();
			case YAML -> new YamlDatabase();
			default -> throw new InvalidDatabaseTypeException();
		}
		return null;
	}
	
}
