package org.anarres.linguistics.core;

import java.util.Locale;

public abstract class Pluralizer extends Base {
	public Pluralizer(Locale locale) {
		super(locale);
	}
	public abstract String getPlural(String word);
}
