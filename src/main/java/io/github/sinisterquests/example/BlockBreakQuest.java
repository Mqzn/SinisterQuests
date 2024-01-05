package io.github.sinisterquests.example;

import io.github.sinisterquests.api.base.Quest;
import io.github.sinisterquests.api.base.progress.ProgressEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public final class BlockBreakQuest extends Quest {
	
	public BlockBreakQuest() {
		super("block-break", 25);
		addLevel(2, 50);
		addLevel(3, 100);
	}
	
	
	@Override
	protected Component display() {
		return Component.text("Great Miner", NamedTextColor.YELLOW);
	}
	
	@Override
	protected ProgressEvent event() {
		return new BlockBreakProgressEvent(this);
	}
	
	@Override
	public void reward(Player player, int levelCompleted) {
		player.sendMessage("Here's a kiss to the cheeks :P");
	}
}
