package io.github.sinisterquests.listeners;

import io.github.sinisterquests.api.SinisterQuestAPIProvider;
import io.github.sinisterquests.api.base.PlayerQuestContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class RegistryListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		SinisterQuestAPIProvider.get().getDatabase().loadPlayerQuests(player.getUniqueId())
			.whenComplete((questsContainer, ex) -> {
				if (ex != null) {
					ex.printStackTrace();
					return;
				}
				SinisterQuestAPIProvider.get().getManager()
					.setPlayerContainer(questsContainer);
			});
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		PlayerQuestContainer container = SinisterQuestAPIProvider.get().getManager()
			.getPlayerQuests(player.getUniqueId()).orElse(null);
		if (container == null) return;
		
		SinisterQuestAPIProvider.get().getDatabase()
			.unloadAndSave(player.getUniqueId(), container)
			.whenComplete((v, ex) -> {
				SinisterQuestAPIProvider.get().getManager().unCachePlayer(player.getUniqueId());
				System.out.println("Saved " + player.getName() + "'s progress on his quests");
			});
	}
}
