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
     *
     * @param s string to replace
     * @return string with replaced substring
     */
    public static String htmlSpecialChars(String s) {
        return s.replaceAll("&amp;", "&").replaceAll("&lt;", "<").
                replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
    }
    /**
     * Replaces all occurrences of the dotes in the Date.
     *
     * @param s string to replace
     * @return string with replaced substring
     */
    public static String dataValidationFromForm(String s) {
        return s.replace(".", "-");
    }
}

