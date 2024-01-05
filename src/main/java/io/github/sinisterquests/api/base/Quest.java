package io.github.sinisterquests.api.base;

import io.github.sinisterquests.api.base.progress.ProgressEvent;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class Quest {
	
	@Getter
	private final String name;
	
	@Getter
	private final Component display;
	
	@Getter
	private final ProgressEvent event;
	
	private final Map<Integer, QuestLevel> levels = new HashMap<>();
	
	
	protected Quest(String name, int basicGoal) {
		this.name = name;
		this.display = display();
		this.event = event();
		addLevel(new QuestLevel(1, basicGoal));
	}
	
	
	protected abstract Component display();
	
	protected abstract ProgressEvent event();
	
	public abstract void reward(Player player, int levelCompleted);
	
	public void addLevel(QuestLevel level) {
		levels.putIfAbsent(level.level(), level);
	}
	
	public void addLevel(int level, int levelGoal) {
		addLevel(new QuestLevel(level, levelGoal));
	}
	
	public Optional<QuestLevel> getLevel(int level) {
		return Optional.ofNullable(levels.get(level));
	}
	
	public Collection<? extends QuestLevel> getLevels() {
		return levels.values();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Quest quest)) return false;
		return Objects.equals(name, quest.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
}
