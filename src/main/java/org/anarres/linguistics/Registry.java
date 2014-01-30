package org.anarres.linguistics;

import java.util.Locale;
import java.util.Map;

import org.anarres.linguistics.core.*;
import org.anarres.linguistics.en.*;

public class Registry {

    private static class Key<T> {

        private final Locale locale;
        private final Class<T> iface;

        public Key(Locale locale, Class<T> iface) {
            this.locale = locale;
            this.iface = iface;
        }

        @Override
        public int hashCode() {
            return locale.hashCode() ^ iface.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Key))
                return false;
            Key k = (Key) o;
            return locale.equals(k.locale)
                    && iface.equals(k.iface);
        }
    }

    private static Map<Key<Object>, Object> handlers;

    static {
        add(Locale.UK, Pluralizer.class, new EnglishPluralizer(Locale.UK));
        add(Locale.US, Pluralizer.class, new EnglishPluralizer(Locale.US));
    }

    public static <T> T get(Locale locale, Class<T> iface) {
        Object obj = handlers.get(new Key<T>(locale, iface));
        if (obj == null)
            throw new IllegalArgumentException(
                    "No handler available for " + locale + " and " + iface
            );
        return (T) obj;
    }

    public static <T> void add(Locale locale, Class<T> iface, T obj) {
        handlers.put(new Key(locale, iface), obj);
    }

}
