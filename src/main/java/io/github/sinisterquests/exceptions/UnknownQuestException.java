package io.github.sinisterquests.exceptions;

import io.github.sinisterquests.api.base.Quest;

public final class UnknownQuestException extends Exception {
	
	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 */
	public UnknownQuestException(Quest quest) {
		super("The quest '" + quest.getName() + "' is NOT registered !");
	}
}
