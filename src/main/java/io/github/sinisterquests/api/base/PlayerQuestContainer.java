package io.github.sinisterquests.api.base;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class PlayerQuestContainer implements Iterable<PlayerQuest> {
	
	@Getter
	private final UUID uuid;
	
	private final Map<String, PlayerQuest> playerQuestMap = new HashMap<>();
	
	public PlayerQuestContainer(UUID uuid) {
		this.uuid = uuid;
	}
	
	public Optional<PlayerQuest> getPlayerQuest(String name) {
		return Optional.ofNullable(playerQuestMap.get(name));
	}
	
	public Optional<PlayerQuest> getPlayerQuest(Quest quest) {
		return getPlayerQuest(quest.getName());
	}
	
	public void updateQuest(PlayerQuest quest) {
		playerQuestMap.computeIfPresent(quest.getQuest().getName(), (id, q) -> quest);
	}
	
	public void setQuest(String name, PlayerQuest quest) {
		playerQuestMap.put(name, quest);
	}
	
	public void assignQuest(Quest quest) {
		playerQuestMap.put(quest.getName(), new PlayerQuest(uuid, quest));
	}
	
	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@NotNull
	@Override
	public Iterator<PlayerQuest> iterator() {
		return playerQuestMap.values().iterator();
	}
	
}
