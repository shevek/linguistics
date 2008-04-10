package org.anarres.linguistics.en;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.anarres.linguistics.core.Conjugator;
import org.anarres.linguistics.core.Conjugation;

import static org.anarres.linguistics.en.EnglishConjugation.*;

public class EnglishConjugator extends Conjugator {

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

	public EnglishConjugator(Locale locale) {
		super(locale);
		setConjugation(EnglishConjugation.values());

		irregular = new HashMap<String,Verb>();

		/* Fixed irregular present. */
		for (String word : new String[] {
			"did", "had", "ate", "made", "put",  
			"spent", "fought", "sank", "gave", "sought",
			"shall", "could", "ought", "should",
		}) {
			addFixed(word);
		}

		/* Fixed irregular present. */
		for (String word : new String[] {
			"thought", "saw", "bent", "will", "might", "cut",
		}) {
			addFixed(word);
		}

		/* Fixed irregular non-present. */

		addVerb("be",   "am", "are", "is", "are");
		addVerb("can",  "can", "can", "can", "can");
		addVerb("cannot",  "cannot", "cannot", "cannot", "cannot");
		addVerb("do",  "do", "do", "does", "do");
		addVerb("go",  "go", "go", "goes", "go");
		addVerb("have", "have", "have", "has", "have");
		addVerb("would",  "would", "would", "would", "would");

		// addVerb("was",  "was", "were", "was", "were");
	}

	public void addVerb(String root,
			String s1, String s2, String s3, String pl) {
		irregular.put(root, new Verb(s1, s2, s3, pl));
	}

	private void addFixed(String root) {
		addVerb(root, root, root, root, root);
	}

	public String getConjugation(String orig, Conjugation c) {
		String	word = orig.toLowerCase();

		Verb	v = irregular.get(word);
		if (v != null) return v.getConjugation(c);

		if (c.getValue() != HE)
			return orig;
		switch (word.charAt(word.length() - 1)) {
			case 'y':
				switch (word.charAt(word.length() - 2)) {
					case 'a': case 'e': case 'i': case 'o': case 'u':
						// -ya -ey -iy -oy -uy
						return orig + "s";
					default:
						// -y
						return prefix(orig, 1) + "ies";
				}
			case 'h':
				switch (word.charAt(word.length() - 2)) {
					case 'c': case 's':
						// -ch -sh
						return orig + "es";
					default:
						// -h
						return orig + "s";
				}
			case 'z':
				switch (word.charAt(word.length() - 2)) {
					case 'a': case 'e': case 'i': case 'o': case 'u':
						// -az -ez -iz -oz -uz
						return orig + "zes";
					case 'z':
						// -zz
						return orig + "es";
					default:
						// -z
						return prefix(orig, 1) + "ies";
				}
			case 's':
				// -s
				return orig + "es";
			default:
				return orig + "s";
		}
	}

}
