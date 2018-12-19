package ch.epfl.sweng.studdybuddy.util;

import java.util.Arrays;
import java.util.List;

/**
 * Class containing different emoji icons for the offered languages
 */
public class Language {
    public static final String EN = "\uD83C\uDDEC\uD83C\uDDE7";
    public static final String FR = "\uD83C\uDDEB\uD83C\uDDF7";
    public static final String GER = "\uD83C\uDDE9\uD83C\uDDEA";
    public static final String IT = "\uD83C\uDDEE\uD83C\uDDF9";
    public static final List<String> languages = Arrays.asList(EN,FR, GER,IT);

    /**
     * return an int depending on the fixed position of the language codes
     * @param language the string representing the language emoji
     * @return the position of the language in the list of languages
     */
    public static int LanguageToInt(String language){

        switch(language)
        {
            case "\uD83C\uDDEC\uD83C\uDDE7":
                return 0;
            case "\uD83C\uDDEB\uD83C\uDDF7":
                return 1;
            case "\uD83C\uDDE9\uD83C\uDDEA":
                return 2;
            case "\uD83C\uDDEE\uD83C\uDDF9":
                return 3;
            default:
                return 0;
        }

    }
}
