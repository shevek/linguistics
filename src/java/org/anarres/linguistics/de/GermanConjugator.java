package org.anarres.linguistics.de;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.anarres.linguistics.core.Conjugator;

import static org.anarres.linguistics.de.GermanConjugation.*;

/**
 */
// @see http://www.utils.ex.ac.uk/german/abinitio/verbs/
public class GermanConjugator extends Conjugator<GermanConjugation> {

/*
	private static class Verb {
		private String	s1;
		private String	s2;
		private String	s3;
		private String	pl;
		public Verb(String s1, String s2, String s3, String pl) {
			this.s1 = s1;
			this.s2 = s2;
			this.s3 = s3;
			this.pl = pl;
		}
		public String getConjugation(Conjugation c) {
			switch ((EnglishConjugation)c.getValue()) {
				case I:
					return s1;
				case YOU_S:
					return s2;
				case HE:
					return s3;
				case WE:
				case YOU_PL:
				case THEY:
					return pl;
			}
			throw new IllegalArgumentException(
					"Illegal conjugation requested: " + c
						);
		}
	}

	private Map<String,Verb>	irregular;
*/

	private Map<String,String[]>	conjugations;

	public GermanConjugator(Locale locale) {
		super(locale);
		setConjugation(GermanConjugation.values());

		// irregular = new HashMap<String,Verb>();
		conjugations.put("",
			new String[] { "e", "st", "t", "en", "t", "en" });
		conjugations.put("den",
			new String[] { "e", "st", "et", "en", "et", "en" });
		conjugations.put("eln",
			new String[] { "e", "st", "t", "n", "t", "n" });
		conjugations.put("ern",
			new String[] { "e", "st", "t", "n", "t", "n" });
		conjugations.put("ieren",
			new String[] { "e", "st", "t", "en", "t", "en" });
		conjugations.put("men",	// only after consonant
			new String[] { "e", "est", "et", "en", "et", "en" });
		conjugations.put("nen",	// only after consonant
			new String[] { "e", "est", "et", "en", "et", "en" });
		conjugations.put("sen",
			new String[] { "e", "t", "t", "en", "t", "en" });
		conjugations.put("ssen",
			new String[] { "e", "t", "t", "en", "t", "en" });
		conjugations.put("ten",
			new String[] { "e", "est", "et", "en", "et", "en" });
		conjugations.put("zen",
			new String[] { "e", "t", "t", "en", "t", "en" });
	}

	public String getConjugation(String orig, GermanConjugation c) {
		String	word = orig.toLowerCase();

/*
		Verb	v = irregular.get(word);
		if (v != null) return v.getConjugation(c);
*/

		for (int i = 5; i >= 0; i--) {
			String[]	conj = conjugations.get(suffix(word, i));
			if (conj != null)
				return conj[c.ordinal()];
		}
		/* The map above contains "". */
		throw new IllegalStateException();
	}

}
