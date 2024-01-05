package io.github.sinisterquests;

import io.github.mqzn.commands.SpigotCommandManager;
import io.github.mqzn.commands.SpigotCommandRequirement;
import io.github.mqzn.commands.base.Command;
import io.github.mqzn.commands.base.CommandInfo;
import io.github.sinisterquests.api.SinisterQuestAPI;
import io.github.sinisterquests.api.SinisterQuestAPIProvider;
import io.github.sinisterquests.api.base.QuestManager;
import io.github.sinisterquests.api.base.impl.SimpleQuestManager;
import io.github.sinisterquests.api.database.DatabaseFactory;
import io.github.sinisterquests.api.database.QuestDatabase;
import io.github.sinisterquests.exceptions.InvalidDatabaseTypeException;
import io.github.sinisterquests.gui.PlayerQuestGui;
import io.github.sinisterquests.listeners.RegistryListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class SinisterQuests extends JavaPlugin implements SinisterQuestAPI {
	
	private QuestDatabase database;
	private QuestManager manager;
	
	private SpigotCommandManager commandManager;
	
	@Override
	public void onEnable() {
		// Plugin startup logic
		SinisterQuestAPIProvider.load(this);
		
		this.getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();
		
		String databaseType = getConfig().getString("database-type");
		if (databaseType == null) throw new InvalidDatabaseTypeException();
		
		database = DatabaseFactory.getFactory().createDatabase(databaseType);
		database.initialize();
		
		manager = new SimpleQuestManager(this);
		
		commandManager = new SpigotCommandManager(this);
		commandManager.registerCommand(questCommand());
		
		Bukkit.getPluginManager().registerEvents(new RegistryListener(), this);
	}
	
	private Command<CommandSender> questCommand() {
		return Command.builder(commandManager, "quests")
			.info(new CommandInfo("sinisterquests.command", "Main command for viewing your quests"))
			.requirement(SpigotCommandRequirement.ONLY_PLAYER_EXECUTABLE)
			.defaultExecutor(((sender, commandContext) -> {
				Player player = (Player) sender;
				PlayerQuestGui gui = new PlayerQuestGui(player);
				gui.open(player);
			}))
			.build();
	}
	
	@Override
	public @NotNull FileConfiguration config() {
		return getConfig();
	}
	
	@Override
	public QuestDatabase getDatabase() {
		return database;
	}
	
	@Override
	public QuestManager getManager() {
		return manager;
	}
	
}
