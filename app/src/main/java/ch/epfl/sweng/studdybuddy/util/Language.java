package ch.epfl.sweng.studdybuddy.util;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;

public class Language {
    public static final String EN = "\uD83C\uDDEC\uD83C\uDDE7";
    public static final String FR = "\uD83C\uDDEB\uD83C\uDDF7";
    public static final String GER = "\uD83C\uDDE9\uD83C\uDDEA";
    public static final String IT = "\uD83C\uDDEE\uD83C\uDDF9";
    public static final List<String> languages = Arrays.asList(EN,FR, GER,IT);

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
