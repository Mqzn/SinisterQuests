package io.github.sinisterquests.api.events;

import io.github.sinisterquests.api.base.Quest;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerQuestCompletionEvent extends PlayerEvent {
	
	private final static HandlerList handlers = new HandlerList();
	
	@Getter
	private final Quest quest;
	
	public PlayerQuestCompletionEvent(@NotNull Player who, Quest quest) {
		super(who);
		this.quest = quest;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}
}
