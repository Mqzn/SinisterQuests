package io.github.sinisterquests.api.events;

import io.github.sinisterquests.api.base.Quest;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerQuestProgressEvent extends PlayerEvent {
	
	private final static HandlerList handlers = new HandlerList();
	@Getter
	private final Quest quest;
	@Getter
	private final int oldCount;
	@Getter
	private final int newCount;
	
	public PlayerQuestProgressEvent(@NotNull Player who, Quest quest, int oldCount, int newCount) {
		super(who);
		this.quest = quest;
		this.oldCount = oldCount;
		this.newCount = newCount;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}
}
