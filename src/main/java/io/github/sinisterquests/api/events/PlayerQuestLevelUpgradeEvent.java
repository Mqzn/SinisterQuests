package io.github.sinisterquests.api.events;

import io.github.sinisterquests.api.base.Quest;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerQuestLevelUpgradeEvent extends PlayerEvent {
	
	private final static HandlerList handlers = new HandlerList();
	@Getter
	private final Quest quest;
	@Getter
	private final int oldLevel;
	@Getter
	private final int newLevel;
	
	public PlayerQuestLevelUpgradeEvent(@NotNull Player who, Quest quest, int oldLevel, int newLevel) {
		super(who);
		this.quest = quest;
		this.oldLevel = oldLevel;
		this.newLevel = newLevel;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}
}
