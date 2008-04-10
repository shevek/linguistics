package org.anarres.linguistics.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public abstract class Conjugator<E extends Enum> extends Base {

	private List<E>	conj;

	public Conjugator(Locale locale) {
		super(locale);
	}

	protected void setConjugation(E[] values) {
		this.conj = Collections.unmodifiableList(Arrays.asList(values));
	}

	public List<E> getConjugations() {
		return conj;
	}

	public abstract String getConjugation(String word, E c);
}
