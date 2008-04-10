package org.anarres.linguistics.core;

import java.util.Locale;

/**
 * A pluralizer for nouns.
 */
public abstract class Pluralizer extends Base {
	protected Pluralizer(Locale locale) {
		super(locale);
	}

	/**
	 * Returns the plural form of the given noun.
	 */
	public abstract String getPlural(String noun);
}
