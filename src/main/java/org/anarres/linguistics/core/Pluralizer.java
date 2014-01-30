package org.anarres.linguistics.core;

import java.util.Locale;
import javax.annotation.Nonnull;

/**
 * A pluralizer for nouns.
 */
public abstract class Pluralizer extends Base {

    protected Pluralizer(@Nonnull Locale locale) {
        super(locale);
    }

    /**
     * Returns the plural form of the given noun.
     */
    @Nonnull
    public abstract String getPlural(@Nonnull String noun);
}
