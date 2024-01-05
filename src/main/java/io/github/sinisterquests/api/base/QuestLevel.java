package io.github.sinisterquests.api.base;

import org.bson.Document;

public record QuestLevel(int level, int maxCount) {
	
	public Document asDoc() {
		return new Document("level", level).append("goal", maxCount);
	}
}
