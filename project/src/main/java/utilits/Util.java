package utilits;

/**
 * Util class for working with strings.
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class Util {
    /**
     * Replaces all occurrences of the specified substring in the specified
     *
     * @param s string to replace
     * @return string with replaced substring
     */
    public static String htmlSpecialChars(String s){
        return s.replaceAll("&amp;","&").replaceAll("&lt;","<").
                replaceAll("&gt;",">").replaceAll(   "&quot;","\"");
    }

    public static String dataValidationFromForm(String s){
        return s.replace(".","-");
    }
}
