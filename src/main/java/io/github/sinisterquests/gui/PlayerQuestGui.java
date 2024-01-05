package io.github.sinisterquests.gui;

import fr.mrmicky.fastinv.FastInv;
import io.github.sinisterquests.api.SinisterQuestAPIProvider;
import io.github.sinisterquests.api.base.PlayerQuest;
import io.github.sinisterquests.api.base.PlayerQuestContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PlayerQuestGui extends FastInv {
	
	public PlayerQuestGui(Player player) {
		super((holder) -> Bukkit.createInventory(holder, 8,
			Component.text("View your quests", NamedTextColor.GREEN)));
		
		PlayerQuestContainer container = SinisterQuestAPIProvider.get().getManager()
			.getPlayerQuests(player.getUniqueId()).orElse(null);
		if (container == null) return;
		
		for (PlayerQuest quest : container) {
			addItem(quest.displayAsItemStack());
		}
	}
}
