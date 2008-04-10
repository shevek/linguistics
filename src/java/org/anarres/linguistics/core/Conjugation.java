package org.anarres.linguistics.core;

import java.util.Locale;

public class Conjugation implements Comparable<Conjugation> {

	private Locale	locale;
	private String	name;
	private Object	value;

	/* pp */ Conjugation(Locale locale, String name, Object value) {
		this.locale = locale;
		this.name = name;
		this.value = value;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public int compareTo(Conjugation other) {
		if (value instanceof Comparable)
			return ((Comparable)value).compareTo(other.value);
		return 0;
	}

	public int hashCode() {
		return locale.hashCode() ^ value.hashCode();
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Conjugation))
			return false;
		Conjugation	c = (Conjugation)o;
		return locale.equals(c.locale) && value.equals(c.value);
	}

	public String toString() {
		return name;
	}

}
