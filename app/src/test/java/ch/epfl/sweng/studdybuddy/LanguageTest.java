package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.Arrays;

import ch.epfl.sweng.studdybuddy.util.Language;

import static junit.framework.TestCase.assertEquals;
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

    @Test
    public void EnglishToZero(){
        assertEquals(0, Language.LanguageToInt("\uD83C\uDDEC\uD83C\uDDE7"));
    }
    @Test
    public void FrenchToOne(){
        assertEquals(1, Language.LanguageToInt("\uD83C\uDDEB\uD83C\uDDF7"));
    }
    @Test
    public void GermanToTwo(){
        assertEquals(2, Language.LanguageToInt("\uD83C\uDDE9\uD83C\uDDEA"));
    }
    @Test
    public void ItalianToThree(){
        assertEquals(3, Language.LanguageToInt("\uD83C\uDDEE\uD83C\uDDF9"));
    }
    @Test
    public void DefaultToZero(){
        assertEquals(0, Language.LanguageToInt("asdasd"));
    }
    @Test
    public void ListOfLanguages(){
        Language language = new Language();
        assertEquals(Arrays.asList("\uD83C\uDDEC\uD83C\uDDE7","\uD83C\uDDEB\uD83C\uDDF7",
                "\uD83C\uDDE9\uD83C\uDDEA","\uD83C\uDDEE\uD83C\uDDF9"), language.languages);
    }

}
