package io.github.sinisterquests.base;

import io.github.sinisterquests.base.progress.LevelProgress;
import lombok.Getter;

import java.util.*;

public final class PlayerQuest {
	
	@Getter
	private final UUID uuid;
	
	@Getter
	private final Quest quest;
	
	@Getter
	private int currentLevel = 1;
	
	private final Map<Integer, LevelProgress> levelProgress = new HashMap<>();
	
	public PlayerQuest(UUID uuid, Quest quest) {
		this.uuid = uuid;
		this.quest = quest;
		for(QuestLevel level : quest.getLevels()) {
			levelProgress.put(level.level(), new LevelProgress(level));
		}
	}
	
	public void incrementLevel() {
		currentLevel++;
	}
	
	public Optional<LevelProgress> getLevelProgress(int level){
		if(level > currentLevel) {
			return Optional.empty();
		}
		var questLvl = quest.getLevel(level);
		return questLvl.map(levelProgress::get);
	}
	
	public Optional<LevelProgress> getCurrentProgress(){
		return getLevelProgress(currentLevel);
	}
	
	public boolean hasNextLevel() {
		return levelProgress.get(currentLevel+1) != null;
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
