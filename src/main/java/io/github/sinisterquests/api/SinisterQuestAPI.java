package io.github.sinisterquests.api;

import io.github.sinisterquests.api.base.QuestManager;
import io.github.sinisterquests.api.database.QuestDatabase;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public interface SinisterQuestAPI {
	
	@NotNull FileConfiguration config();
	
	QuestDatabase getDatabase();
	
	QuestManager getManager();
	
}
