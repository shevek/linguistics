package org.anarres.linguistics.core;

import java.util.Locale;

public abstract class Base {
	private Locale	locale;

	public Base(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	/** Returns 'word' with the last 'trim' characters removed. */
	protected String prefix(String word, int trim) {
		return word.substring(0, word.length() - trim);
	}
}
