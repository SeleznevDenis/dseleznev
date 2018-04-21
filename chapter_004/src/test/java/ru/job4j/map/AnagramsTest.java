package ru.job4j.map;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test Anagrams.
 * @author Denis Seleznev
 * @version $Id$
 * @since 21.04.2018
 */
public class AnagramsTest {

    @Test
    public void ifTwoWordsAreAnagramsThenCheckShouldReturnTrue() {
        Anagrams testAnagrams = new Anagrams();
        Boolean result = testAnagrams.checkWords("мама", "амам");
        assertThat(result, is(true));
    }

    @Test
    public void ifTwoWordsAreNotAnagramsThenCheckShouldReturnFalse() {
        Anagrams testAnagrams = new Anagrams();
        assertThat(testAnagrams.checkWords("мама", "папа"), is(false));
    }

    @Test
    public void ifDifferentNumberOfIdenticalLettersCheckShouldReturnFalse() {
        Anagrams testAnagrams = new Anagrams();
        assertThat(testAnagrams.checkWords("мама", "мааа"), is(false));
    }

    @Test
    public void ifFirstWordIsLongerThenSecondCheckShouldReturnFalse() {
        Anagrams testAnagrams = new Anagrams();
        assertThat(testAnagrams.checkWords("мамаа", "мама"), is(false));
    }
}