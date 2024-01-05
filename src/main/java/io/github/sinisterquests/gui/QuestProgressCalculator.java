package io.github.sinisterquests.gui;

import io.github.sinisterquests.api.base.PlayerQuest;
import io.github.sinisterquests.api.base.progress.LevelProgress;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class QuestProgressCalculator implements ProgressCalculator {
	
	private int progressed, goal;
	
	public QuestProgressCalculator(PlayerQuest quest) {
		LevelProgress progress = quest.getCurrentProgress().orElse(null);
		if (progress == null) {
			return;
		}
		goal = progress.getCount();//total
		int progressedAmount = progress.getCount();
		
		int percentage = (progressedAmount / goal) * 100;
		this.progressed = percentage * NUMBER_OF_SQUARES;
		
	}
	
	@Override
	public char progressChar() {
		return '■';
	}
	
	@Override
	public int progressedChars() {
		return progressed;
	}
	
	/**
	 * This is mostly used in action bars or scoreboards
	 *
	 * @return to display next to the progress bar
	 * @author Mqzen
	 * @date 7/22/2022
	 */
	@Override
	public Component infoDisplay() {
		return Component.text("(" + progressed + "/" + goal + ")", NamedTextColor.GRAY);
	}
}
