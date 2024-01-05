package io.github.sinisterquests.api.base.progress;

import io.github.sinisterquests.api.base.Quest;
import org.bukkit.event.Listener;

public interface ProgressEvent extends Listener {
	
	Quest quest();
	
}
