package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.util.Language;

import static junit.framework.TestCase.assertTrue;

public class LanguageTest {
    @Test
    public void CorrectLangTest(){
        assertTrue(Language.EN.equals("\uD83C\uDDEC\uD83C\uDDE7"));
        assertTrue(Language.FR.equals("\uD83C\uDDEB\uD83C\uDDF7"));
        assertTrue(Language.GER.equals("\uD83C\uDDE9\uD83C\uDDEA"));
        assertTrue(Language.IT.equals("\uD83C\uDDEE\uD83C\uDDF9"));
        assertTrue(Language.languages.size() == 4);
    }
}
