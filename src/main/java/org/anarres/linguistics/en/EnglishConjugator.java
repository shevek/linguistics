package org.anarres.linguistics.en;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.anarres.linguistics.core.Conjugator;

import static org.anarres.linguistics.en.EnglishConjugation.*;

public class EnglishConjugator extends Conjugator<EnglishConjugation> {

    private static class Verb {

        private final String s1;
        private final String s2;
        private final String s3;
        private final String pl;

        public Verb(String s1, String s2, String s3, String pl) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.pl = pl;
        }

        public String getConjugation(EnglishConjugation c) {
            switch (c) {
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

    private final Map<String, Verb> irregular;

    public EnglishConjugator(Locale locale) {
        super(locale);
        setConjugation(EnglishConjugation.values());

        irregular = new HashMap<String, Verb>();

        /* Fixed irregular present. */
        for (String word : new String[]{
            "did", "had", "ate", "made", "put",
            "spent", "fought", "sank", "gave", "sought",
            "shall", "could", "ought", "should",}) {
            addFixed(word);
        }

        /* Fixed irregular present. */
        for (String word : new String[]{
            "thought", "saw", "bent", "will", "might", "cut",}) {
            addFixed(word);
        }

        /* Fixed irregular non-present. */
        addIrregular("be", "am", "are", "is", "are");
        addIrregular("can", "can", "can", "can", "can");
        addIrregular("cannot", "cannot", "cannot", "cannot", "cannot");
        addIrregular("do", "do", "do", "does", "do");
        addIrregular("go", "go", "go", "goes", "go");
        addIrregular("have", "have", "have", "has", "have");
        addIrregular("would", "would", "would", "would", "would");

        // addIrregular("was",  "was", "were", "was", "were");
    }

    public void addIrregular(String root,
            String s1, String s2, String s3, String pl) {
        irregular.put(root, new Verb(s1, s2, s3, pl));
    }

    private void addFixed(String root) {
        addIrregular(root, root, root, root, root);
    }

    @Override
    public String getConjugation(String orig, EnglishConjugation c) {
        String word = orig.toLowerCase();

        Verb v = irregular.get(word);
        if (v != null)
            return v.getConjugation(c);

        if (c != HE)
            return orig;
        switch (word.charAt(word.length() - 1)) {
            case 'y':
                switch (word.charAt(word.length() - 2)) {
                    case 'a':
                    case 'e':
                    case 'i':
                    case 'o':
                    case 'u':
                        // -ya -ey -iy -oy -uy
                        return orig + "s";
                    default:
                        // -y
                        return prefix(orig, 1) + "ies";
                }
            case 'h':
                switch (word.charAt(word.length() - 2)) {
                    case 'c':
                    case 's':
                        // -ch -sh
                        return orig + "es";
                    default:
                        // -h
                        return orig + "s";
                }
            case 'z':
                switch (word.charAt(word.length() - 2)) {
                    case 'a':
                    case 'e':
                    case 'i':
                    case 'o':
                    case 'u':
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
