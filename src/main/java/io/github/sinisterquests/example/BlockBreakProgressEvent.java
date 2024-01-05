package io.github.sinisterquests.example;

import io.github.sinisterquests.api.SinisterQuestAPIProvider;
import io.github.sinisterquests.api.base.Quest;
import io.github.sinisterquests.api.base.progress.ProgressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public final class BlockBreakProgressEvent implements ProgressEvent {
	
	private final BlockBreakQuest quest;
	
	public BlockBreakProgressEvent(BlockBreakQuest quest) {
		this.quest = quest;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		SinisterQuestAPIProvider.get().getManager()
			.progressPlayer(player, quest);
	}
	
	@Override
	public Quest quest() {
		return quest;
	}
}
