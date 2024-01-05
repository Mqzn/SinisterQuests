package io.github.sinisterquests.api.base.progress;

import io.github.sinisterquests.api.base.QuestLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

public final class LevelProgress {
	
	@Getter
	private final QuestLevel level;
	
	@Getter
	private final int maxCount;
	
	@Getter
	@Setter
	private int count;
	
	public LevelProgress(QuestLevel level) {
		this.level = level;
		this.maxCount = level.maxCount();
	}
	
	public void increment() {
		count++;
	}
	
	public Document asDoc() {
		return new Document("level", level.level())
			.append("goal", maxCount)
			.append("progress-count", count);
	}
}
