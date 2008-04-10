package org.anarres.linguistics.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public abstract class Conjugator extends Base {

	private List<Conjugation>	conj;

	public Conjugator(Locale locale) {
		super(locale);
	}

	protected void setConjugation(String[] names) {
		Conjugation[]	conj = new Conjugation[names.length];
		for (int i = 0; i < conj.length; i++)
			conj[i] = new Conjugation(getLocale(),
					names[i], Integer.valueOf(i));
		this.conj = Collections.unmodifiableList(Arrays.asList(conj));
	}

	protected void setConjugation(Enum[] values) {
		Conjugation[]	conj = new Conjugation[values.length];
		for (int i = 0; i < conj.length; i++)
			conj[i] = new Conjugation(getLocale(),
					values[i].name(), values[i]);
		this.conj = Collections.unmodifiableList(Arrays.asList(conj));
	}

	public List<Conjugation> getConjugations() {
		return conj;
	}

	public abstract String getConjugation(String word, Conjugation c);
}
