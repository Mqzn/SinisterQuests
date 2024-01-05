package io.github.sinisterquests.api;

public final class SinisterQuestAPIProvider {
	
	private static SinisterQuestAPI api;
	
	private SinisterQuestAPIProvider() {
		throw new UnsupportedOperationException();
	}
	
	public static void load(SinisterQuestAPI questAPI) {
		api = questAPI;
	}
	
	public static SinisterQuestAPI get() {
		return api;
	}
	
}
