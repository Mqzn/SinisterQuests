package club.vades.duels.progress;

import org.bukkit.ChatColor;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface ProgressCalculator {
	
	int NUMBER_OF_SQUARES = 8;
	
	int input();
	
	double percentage();
	
	char progressChar();
	
	int progressedChars();
	
	/**
	 * This is mostly used in action bars or scoreboards
	 *
	 * @return to display next to the progress bar
	 * @author Mqzen
	 * @date 7/22/2022
	 */
	String infoDisplay();
	
	
	default String drawBar(@NonNull ChatColor progressedColor,
	                       @NonNull ChatColor notProgressedColor) {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < NUMBER_OF_SQUARES; i++) {
			if (i <= progressedChars()) {
				sb.append(progressedColor);
			} else {
				sb.append(notProgressedColor);
			}
			sb.append(this.progressChar()); //
		}
		
		return sb.append(" ").append(this.infoDisplay()).toString();
	}
}
