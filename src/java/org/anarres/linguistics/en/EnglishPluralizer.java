package org.anarres.linguistics.en;

import java.util.HashMap;
import java.util.Map;

import org.anarres.linguistics.core.Pluralizer;

public class EnglishPluralizer implements Pluralizer {

	/* Priority only matters if any one suffix is a substring of
     * another. */
	private static class Rule {
		private String	tail;
		private int		priority;
		public Rule(String tail, int priority) {
			this.tail = tail;
			this.priority = priority;
		}
	}

	private boolean				classical;
	private Map<String,String>	irregular;
	private Map<String,Rule>	suffixes;

	public EnglishPluralizer() {
		this(true);
	}

	public EnglishPluralizer(boolean classical) {
		irregular = new HashMap<String,String>();
		suffixes = new HashMap<String,Rule>();

		addIrregular("ephemeris"     , "ephemerides");
		addIrregular("iris"          , "irises|irides");
		addIrregular("clitoris"      , "clitorises|clitorides");
		addIrregular("rhinoceros"    , "rhinoceroses|rhinocerotes");
		addIrregular("corpus"        , "corpuses|corpora");
		addIrregular("opus"          , "opuses|opera");
		addIrregular("genus"         , "genera");
		addIrregular("mythos"        , "mythoi");
		addIrregular("penis"         , "penises|penes");
		addIrregular("testis"        , "testes");

		addIrregular("brother"       , "brothers|brethren");
		addIrregular("child"         , "children");
		addIrregular("cyclops"       , "cyclopes");
		addIrregular("die"           , "dice");
		addIrregular("eskimo"        , "eskimos");
		addIrregular("loaf"          , "loaves");
		addIrregular("hoof"          , "hoofs|hooves");
		addIrregular("beef"          , "beefs|beeves");
		addIrregular("money"         , "monies");
		addIrregular("mongoose"      , "mongooses");
		addIrregular("ox"            , "oxen");
		addIrregular("cow"           , "cows|kine");
		addIrregular("soliloquy"     , "soliloquies");
		addIrregular("graffito"      , "graffiti");
		addIrregular("prima donna"   , "prima donnas|prime donne");
		addIrregular("octopus"       , "octopuses|octopodes");
		addIrregular("genie"         , "genies|genii");
		addIrregular("ganglion"      , "ganglions|ganglia");
		addIrregular("trilby"        , "trilbys");
		addIrregular("turf"          , "turfs|turves");
		addIrregular("vax"           , "vaxes|vaxen");
		addIrregular("zero"          , "zeros");

		if (classical)
		addIrregular(new String[] {
			"anathema", "bema", "carcinoma", "charisma", "diploma",
			"dogma", "drama", "edema", "enema", "enigma", "lemma",
			"lymphoma", "magma", "melisma", "miasma", "oedema",
			"sarcoma", "schema", "soma", "stigma", "stoma", "trauma",
			"gumma", "pragma",
		}, "a", "ata");

		addIrregular(new String[] {
			"alumna", "alga", "vertebra", "larva",
		}, "a", "ae");

		if (classical)
		addIrregular(new String[] {
			"amoeba", "antenna", "formula", "hyperbola",
			"medusa", "nebula", "parabola", "abscissa",
			"hydra", "nova", "lacuna", "aurora",
			"persona",	// .*umbra
		}, "a", "ae");

		if (classical)
		addIrregular(new String[] {
			"stamen",       "foramen",      "lumen",
		}, "en", "ina");

		addIrregular(new String[] {
			"bacterium",   "agendum",  "desideratum",  "erratum",
			"stratum",     "datum",    "ovum",         "extremum",
			"candelabrum", "forum",
		}, "um", "a");

		if (classical)
		addIrregular(new String[] {
			"maximum",      "minimum",      "momentum",     "optimum",
			"quantum",      "cranium",      "curriculum",   "dictum",
			"phylum",       "aquarium",     "compendium",   "emporium",
			"enconium",     "gymnasium",    "honorarium",   "interregnum",
			"lustrum",      "memorandum",   "millenium",    "rostrum", 
			"spectrum",     "speculum",     "stadium",      "trapezium",
			"ultimatum",    "medium",       "vacuum",       "velum", 
			"consortium",
		}, "um", "a");

		addIrregular(new String[] {
			"alumnus",  "alveolus", "bacillus", "bronchus",
			"locus",    "nucleus",  "stimulus", "meniscus",
			"hippopotamus",         "cactus",
		}, "us", "i");

		if (classical)
		addIrregular(new String[] {
			"focus",    "radius",   "genius",
			"incubus",  "succubus", "nimbus",
			"fungus",   "nucleolus",    "stylus",
			"torus",    "umbilicus",    "uterus",
		}, "us", "i");

		// Assimilated 4th declension Latin nouns
		if (classical)
		addIrregular(new String[] {
			"status",       "apparatus",    "prospectus",   "sinus",
			"hiatus",       "impetus",      "plexus",
		}, "us", "us");

		addIrregular(new String[] {
			"criterion",    "perihelion",   "aphelion",
			"phenomenon",   "prolegomenon", "noumenon",
			"organon",      "asyndeton",    "hyperbaton",
		}, "on", "a");

		if (classical)
		addIrregular(new String[] {
			"oxymoron",
		}, "on", "a");

		// Italian?
		if (classical)
		addIrregular(new String[] {
			"solo",         "soprano",      "basso",        "alto",
			"contralto",    "tempo",
		}, "o", "i");

		addIrregular(new String[] {
			"albino",       "archipelago",  "armadillo",    "commando",
			"crescendo",    "fiasco",       "ditto",        "dynamo",
			"embryo",       "ghetto",       "guano",        "inferno",
			"jumbo",        "lumbago",      "magneto",      "manifesto",
			"medico",       "octavo",       "photo",        "pro",
			"quarto",       "canto",        "lingo",        "generalissimo",
			"stylo",        "rhino",        "piano",
		}, "o", "os");

		addIrregular(new String[] {
			"codex",    "murex",    "silex",
		}, "ex", "ices");

		if (classical)
		addIrregular(new String[] {
			"vortex",   "vertex",   "cortex",   "latex",
			"pontifex", "apex",     "index",    "simplex",
		}, "ex", "ices");

		// Arabic
		if (classical)
		addIrregular(new String[] {
			"afrit",    "afreet",   "efreet",
		}, "", "i");

		// Hebrew
		if (classical)
		addIrregular(new String[] {
			"goy",      "seraph",   "cherub",
		}, "", "im");

		addIrregular(new String[] {
			"human", "Alabaman", "Bahaman", "Burman", "German",
			"Hiroshiman", "Liman", "Nakayaman", "Oklahoman",
			"Panaman", "Selman", "Sonaman", "Tacoman", "Yakiman",
			"Yokohaman", "Yuman",
		}, "man", "mans");

		addIrregular(new String[] {
		// PAIRS OR GROUPS SUBSUMED TO A SINGULAR...
			"breeches", "britches", "clippers", "gallows", "hijinks",
			"headquarters", "pliers", "scissors", "testes", "herpes",
			"pincers", "shears", "proceedings", "trousers", "measles",

		// UNASSIMILATED LATIN 4th DECLENSION

			"cantus", "coitus", "nexus",

		// RECENT IMPORTS...
			"contretemps", "corps", "debris",
			/* ".*ois", */
			
		// DISEASES
			/* ".*measles", */
			"mumps",

		// MISCELLANEOUS OTHERS...
			"diabetes", "jackanapes", "series", "species", "rabies",
			"chassis", "innings", "news", "mews", "fracas", "means",
			"aircraft", "spacecraft",
		}, "", "");

		addIrregular(new String[] {
		// SOME FISH AND HERD ANIMALS
			/* ".*fish", */
			"tuna", "salmon", "mackerel", "trout", "bream", "sea bass",
			"sea-bass", "carp", "cod", "flounder", "whiting",

			/* ".*deer", ".*sheep", */
			"wildebeest", "swine", "eland", "bison", "moose",
			"elk",

		// ALL NATIONALS ENDING IN -ese
			"Portuguese", "Japanese", "Chinese", "Vietnamese",
			"Burmese", "Lebanese", "Siamese", "Senegalese",
			"Bhutanese", "Sinhalese",

		// DISEASES
			/* ".*pox", */

		// OTHER ODDITIES
			"graffiti", "djinn",
			/* "sperm", */
		}, "", "");

		addIrregular(new String[] {
			/* ".*ss", */
			"acropolis", "aegis", "alias", "arthritis", "asbestos",
			"atlas", "bathos", "bias", "bronchitis", "bursitis",
			"caddis", "cannabis", "canvas", "chaos", "cosmos",
			"dais", "digitalis", "encephalitis", "epidermis",
			"ethos", "gas", "glottis", "hepatitis", "hubris",
			"ibis", "lens", "mantis", "marquis", "metropolis",
			"neuritis", "pathos", "pelvis", "polis", /* "rhinoceros", */
			"sassafras", "tonsillitis", "trellis",
			/* ".*us", */
		}, "s", "ses");

/*
		addSuffix(new String[] {
			"major", "lieutenant", "brigadier", "adjutant",
			"quartermaster",
		});
*/

		addSuffix("ois", "ois", 100);
		addSuffix("deer", "deer", 100);
		addSuffix("sheep", "sheep", 100);
		addSuffix("fish", "fish", 100);
		addSuffix("pox", "pox", 100);
		// addSuffix("measles", "measles", 100);
		addSuffix("umbra", "umbrae", 100);

	// HANDLE FAMILIES OF IRREGULAR PLURALS 

		addSuffix("man", "men", 100);
		addSuffix("mouse", "mice", 100);
		addSuffix("louse", "lice", 100);
		addSuffix("goose", "geese", 100);
		addSuffix("tooth", "teeth", 100);
		addSuffix("foot", "feet", 100);

	// HANDLE UNASSIMILATED IMPORTS

		addSuffix("ceps", "ceps", 90);
		addSuffix("zoon", "zoa", 90);
		addSuffix("cis", "ces", 90);
		addSuffix("sis", "ses", 90);
		addSuffix("xis", "xes", 90);

	// HANDLE INCOMPLETELY ASSIMILATED IMPORTS

		if (classical) addSuffix("trix", "trices", 80);
		if (classical) addSuffix("eau", "eaux", 80);
		if (classical) addSuffix("ieu", "ieux", 80);

	// HANDLE SINGULAR NOUNS ENDING IN ...s OR OTHER SILIBANTS

		addSuffix("ss", "sses", 70);
		addSuffix("us", "uses", 70);

		addSuffix("ch", "ches", 60);
		addSuffix("sh", "shes", 60);
		addSuffix("x", "xes", 60);
		addSuffix("z", "zes", 60);

	// HANDLE ...f -> ...ves

		addSuffix("alf", "alves", 50);
		addSuffix("elf", "elves", 50);
		addSuffix("olf", "olves", 50);
		addSuffix("deaf", "deafs", 50);
		addSuffix("eaf", "eaves", 45);
		addSuffix("life", "lives", 45);
		addSuffix("nife", "nives", 45);
		addSuffix("wife", "wives", 45);
		addSuffix("arf", "arves", 45);

	// HANDLE ...y

		addSuffix("ay", "ays", 40);
		addSuffix("ey", "eys", 40);
		addSuffix("iy", "iys", 40);
		addSuffix("oy", "oys", 40);
		addSuffix("uy", "uys", 40);
		addSuffix("y", "ies", 30);

	// HANDLE ...o

		/* Already handled PL_sb_U_o_os */
		addSuffix("ao", "oas", 20);
		addSuffix("eo", "oes", 20);
		addSuffix("io", "ois", 20);
		addSuffix("oo", "oos", 20);
		addSuffix("uo", "ous", 20);
		addSuffix("o", "oes", 15);

	}

	public void addIrregular(String from, String to) {
		irregular.put(from, to);
	}

	public void addIrregular(String[] words, String from, String to) {
		int	flen = from.length();
		for (String word : words) {
			assert word.endsWith(from);
			int	wlen = word.length();
			addIrregular(word, word.substring(0, wlen - flen) + to);
		}
	}

	public void addSuffix(String from, String to, int priority) {
		suffixes.put(from, new Rule(to, priority));
	}

	private String getPlural(String word, String from, String to) {
		int	flen = from.length();
		int	wlen = word.length();
		return word.substring(0, wlen - flen) + to;
	}

	public String getPlural(String orig) {
		String	word = orig.toLowerCase();

		String	value = irregular.get(word);
		if (value != null) return value;

		int		len = word.length();
		int		priority = 0;
		int		chop = len;
		String	tail = "s";
		for (int i = 5; i > 0; i--) {
			int	start = len - i;
			if (start < 0)
				continue;
			Rule	rule = suffixes.get(word.substring(start));
			if (rule != null) {
				if (rule.priority > priority) {
					priority = rule.priority;
					tail = rule.tail;
					chop = start;
				}
			}
		}

		if (priority <= 35)
			if (Character.isUpperCase(orig.charAt(0)))
				if (orig.endsWith("y"))
					return getPlural(orig, "y", "ys");
		if (priority <= 65)
			if (Character.isUpperCase(orig.charAt(0)))
				if (orig.endsWith("s"))
					return getPlural(orig, "s", "ses");
		if (classical && priority <= 75) {
			if (orig.endsWith("ynx"))
				return getPlural(orig, "ynx", "ynges");
			if (orig.endsWith("inx"))
				return getPlural(orig, "inx", "inges");
			if (orig.endsWith("anx"))
				return getPlural(orig, "anx", "anges");
		}
		if (word.indexOf(' ') != -1) {
			int	idx;
			if ((idx = word.indexOf(" of ")) != -1)
				return getPlural(word.substring(0, idx - 1).trim()) +
					word.substring(idx);
			if ((idx = word.indexOf(" in ")) != -1)
				return getPlural(word.substring(0, idx - 1).trim()) +
					word.substring(idx);
			if ((idx = word.indexOf(" to ")) != -1)
				return getPlural(word.substring(0, idx - 1).trim()) +
					word.substring(idx);
			if ((idx = word.indexOf(" at ")) != -1)
				return getPlural(word.substring(0, idx - 1).trim()) +
					word.substring(idx);
			if ((idx = word.indexOf(" de ")) != -1)
				return getPlural(word.substring(0, idx - 1).trim()) +
					word.substring(idx);
		}

		return word.substring(0, chop) + tail;
	}

}
