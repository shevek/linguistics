package org.anarres.linguistics.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * A conjugation routine for verbs.
 */
public abstract class Conjugator<E extends Enum> extends Base {

	private List<E>	conj;

	protected Conjugator(Locale locale) {
		super(locale);
	}

	/**
	 * Sets the list of allowed conjugations (people).
	 *
	 * This is usually called from a subclass constructor as:
	 * <code>setConjugation(MyConjugation.values());</code>
	 */
	protected void setConjugation(E[] values) {
		this.conj = Collections.unmodifiableList(Arrays.asList(values));
	}

	/**
	 * Returns the list of allowed conjugations (people).
	 *
	 * These will be the values of an Enum.
	 */
	public List<E> getConjugations() {
		return conj;
	}

	/**
	 * Conjugates a verb for the given person.
	 *
	 * @param verb The root form of the verb.
	 * @param person The person with respect to whom the verb is to be conjugated.
	 */
	public abstract String getConjugation(String verb, E person);
}
