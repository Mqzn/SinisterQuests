package io.github.sinisterquests.base.progress;

import io.github.sinisterquests.base.Quest;
import org.bukkit.event.Listener;

public interface ProgressEvent extends Listener {

	Quest quest();
	
}
