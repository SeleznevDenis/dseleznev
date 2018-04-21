package ru.job4j.count;

import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 21.04.2018
 */
public class WordIndexTest {

    @Test
    public void getIndicesForWordShouldReturnAllCorrectIndices()
            throws FileNotFoundException {
        WordIndex testIndex = new WordIndex();
        testIndex.loadFile(getClass().getClassLoader().getResource("testWords.txt").getFile());
        Set<Integer> expect = new LinkedHashSet<>(Arrays.asList(
                20, 42, 44, 106, 108, 170, 172, 232, 234)
        );
        assertThat(testIndex.getIndicesForWord("bells"), is(expect));
    }

    @Test(expected = FileNotFoundException.class)
    public void ifFileNotFoundThenThrowException() throws FileNotFoundException {
        WordIndex testIndex = new WordIndex();
        testIndex.loadFile("incorrect way");
    }
}