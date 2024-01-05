package io.github.sinisterquests.api.base;

import io.github.sinisterquests.Util;
import io.github.sinisterquests.api.SinisterQuestAPIProvider;
import io.github.sinisterquests.api.base.progress.LevelProgress;
import io.github.sinisterquests.gui.ItemBuilder;
import io.github.sinisterquests.gui.QuestProgressCalculator;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class PlayerQuest {
	
	@Getter
	private final UUID uuid;
	
	@Getter
	private final Quest quest;
	private final Map<Integer, LevelProgress> levelProgress;
	@Getter
	private int currentLevel = 1;
	
	public PlayerQuest(UUID uuid, Quest quest) {
		this.uuid = uuid;
		this.quest = quest;
		this.levelProgress = new HashMap<>();
		for (QuestLevel level : quest.getLevels()) {
			levelProgress.put(level.level(), new LevelProgress(level));
		}
	}
	
	public PlayerQuest(UUID uuid, Quest quest, int currentLevel, Map<Integer, LevelProgress> levelProgress) {
		this.uuid = uuid;
		this.quest = quest;
		this.currentLevel = currentLevel;
		this.levelProgress = levelProgress;
	}
	
	public static PlayerQuest fromDoc(Document document) {
		UUID uuid = UUID.fromString(document.getString("uuid"));
		String name = document.getString("name");
		
		Quest quest = SinisterQuestAPIProvider.get().getManager().getQuest(name)
			.orElse(null);
		if (quest == null) return null;
		
		int currentLvl = document.getInteger("current-level");
		Map<Integer, LevelProgress> levelProgress = new HashMap<>();
		Document levelsProgressDoc = document.get("progress", Document.class);
		for (QuestLevel level : quest.getLevels()) {
			int lvlCount = levelsProgressDoc.getInteger(level.level() + "");
			
			LevelProgress lvlProgress = new LevelProgress(level);
			lvlProgress.setCount(lvlCount);
			
			levelProgress.put(level.level(), lvlProgress);
		}
		
		return new PlayerQuest(uuid, quest, currentLvl, levelProgress);
	}
	
	public void incrementLevel() {
		currentLevel++;
	}
	
	public Optional<LevelProgress> getLevelProgress(int level) {
		if (level > currentLevel) {
			return Optional.empty();
		}
		
		return Optional.ofNullable(levelProgress.get(level));
	}
	
	public Optional<LevelProgress> getCurrentProgress() {
		return getLevelProgress(currentLevel);
	}
	
	public boolean hasNextLevel() {
		return levelProgress.get(currentLevel + 1) != null;
	}
	
	public ItemStack displayAsItemStack() {
		return new ItemBuilder(Material.WRITABLE_BOOK)
			.name(quest.getDisplay().appendSpace().append(Component.text(Util.intToRoman(currentLevel))))
			.lore(Component.empty(), new QuestProgressCalculator(this)
				.drawBar(NamedTextColor.GREEN, NamedTextColor.RED))
			.build();
	}
	
	public Document asDoc() {
		Document document = new Document("uuid", uuid.toString())
			.append("name", quest.getName())
			.append("current-level", currentLevel);
		
		Document levelProgresses = new Document();
		for (LevelProgress progress : levelProgress.values()) {
			levelProgresses.append(progress.getLevel().level() + "", progress.getCount());
		}
		
		document.append("progress", levelProgresses);
		return document;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (PlayerQuest) obj;
		return Objects.equals(this.uuid, that.uuid) &&
			Objects.equals(this.quest, that.quest);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uuid, quest);
	}
	
}
