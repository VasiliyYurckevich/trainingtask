package utilits;

/**
 * Util class for working with strings.
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 */
public class Util {
    /**
     * Replaces all occurrences of the specified HTML symbols in the String.

     */
    public static String htmlSpecialChars(String s) {
        return s.replaceAll("&amp;", "&").replaceAll("&lt;", "<").
                replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
    }

}

