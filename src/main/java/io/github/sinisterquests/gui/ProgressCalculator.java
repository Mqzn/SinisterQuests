package io.github.sinisterquests.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface ProgressCalculator {
	
	int NUMBER_OF_SQUARES = 8;
	
	char progressChar();
	
	int progressedChars();
	
	/**
	 * This is mostly used in action bars or scoreboards
	 *
	 * @return to display next to the progress bar
	 * @author Mqzen
	 * @date 7/22/2022
	 */
	Component infoDisplay();
	
	
	default Component drawBar(@NonNull TextColor progressedColor,
	                          @NonNull TextColor notProgressedColor) {
		
		Component sb = Component.empty();
		for (int i = 0; i < NUMBER_OF_SQUARES; i++) {
			TextColor toUse;
			if (i <= progressedChars()) {
				toUse = progressedColor;
			} else {
				toUse = notProgressedColor;
			}
			sb = sb.append(Component.text(this.progressChar(), toUse));
		}
		
		return sb.appendSpace().append(this.infoDisplay());
	}
}
