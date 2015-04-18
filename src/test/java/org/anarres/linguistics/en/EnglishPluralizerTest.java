package org.anarres.linguistics.en;

import java.util.Locale;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

/**
 *
 * @author shevek
 */
public class EnglishPluralizerTest {

    private static final Logger LOG = LoggerFactory.getLogger(EnglishPluralizerTest.class);
    private final EnglishPluralizer classical = new EnglishPluralizer(Locale.UK);
    private final EnglishPluralizer modern = new EnglishPluralizer(Locale.US);

    private void test(String in) {
        String out_classical = classical.getPlural(in);
        String out_modern = modern.getPlural(in);
        LOG.info(in + " -> " + out_classical + " / " + out_modern);
        assertNotNull(out_classical);
        assertNotNull(out_modern);
    }

    @Test
    public void testSomeMethod() {
        test("cow");
        test("sphynx");
        test("protozoon");
        test("dustman");
        test("access");
        test("axis");
        test("goose");
        test("sheep");
        test("deer");
        test("octopus");
        test("cactus");
        test("master at arms");
        test("ace of spades");
    }

}
