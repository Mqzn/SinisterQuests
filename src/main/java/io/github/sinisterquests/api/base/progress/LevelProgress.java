package io.github.sinisterquests.base.progress;

import io.github.sinisterquests.base.QuestLevel;
import lombok.Getter;
import lombok.Setter;

public final class LevelProgress {
	
	@Getter
	private final QuestLevel level;
	
	@Getter
	private final int maxCount;
	
	@Getter @Setter
	private int count;
	
	public LevelProgress(QuestLevel level) {
		this.level = level;
		this.maxCount = level.maxCount();
	}
	
	public void increment() {
		count++;
	}
}
