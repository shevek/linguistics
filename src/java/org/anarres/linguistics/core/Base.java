package org.anarres.linguistics.core;

import java.util.Locale;

/**
 * The base class of all linguistics handlers.
 */
public abstract class Base {
	private Locale	locale;

	protected Base(Locale locale) {
		this.locale = locale;
	}

	/**
	 * Returns the locale of this handler.
	 */
	public Locale getLocale() {
		return locale;
	}

	/** Returns 'word' with the last 'trim' characters removed. */
	protected String prefix(String word, int trim) {
		return word.substring(0, word.length() - trim);
	}

	/** Returns the last 'length' characters of 'word'. */
	protected String suffix(String word, int length) {
		return word.substring(word.length() - length);
	}

}
