package ru.job4j.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test SimpleSet.
 * @author Denis Seleznev
 * @version $Id$
 * @since 02.04.2018
 */
public class SimpleSetTest {

    /**
     * Test add not equals elements.
     */
    @Test
    public void ifAddNotEqualsElementsThenSetReturnThisElements() {
        SimpleSet<Integer> testSet = new SimpleSet<>();
        testSet.add(1);
        testSet.add(2);
        Iterator<Integer> testIterator = testSet.iterator();
        assertThat(testIterator.next(), is(1));
        assertThat(testIterator.next(), is(2));
    }

    /**
     * Test add some equals elements.
     */
    @Test
    public void ifAddEqualsElementsThenSetReturnNotEqualsElements() {
        SimpleSet<Integer> testSet = new SimpleSet<>();
        boolean firstResult = testSet.add(1);
        boolean secondResult = testSet.add(1);
        testSet.add(1);
        testSet.add(2);
        Iterator<Integer> testIterator = testSet.iterator();
        assertThat(firstResult, is(true));
        assertThat(secondResult, is(false));
        assertThat(testIterator.next(), is(1));
        assertThat(testIterator.next(), is(2));
    }

    /**
     * Test iterator.
     */
    @Test
    public void ifAllElementsArePassedIteratorHasNextWasFalse() {
        SimpleSet<Integer> testSet = new SimpleSet<>(40);
        testSet.add(1);
        testSet.add(2);
        testSet.add(3);
        Iterator<Integer> testIterator = testSet.iterator();
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(1));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(2));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(3));
        assertThat(testIterator.hasNext(), is(false));
    }
}