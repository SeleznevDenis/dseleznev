package ru.job4j.set;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test SimpleHashSet.
 * @author Denis Seleznev
 * @version $Id$
 * @since 04.04.2018
 */
public class SimpleHashSetTest {

    /**
     * Test add.
     */
    @Test
    public void ifAddSomeEqualsElementsThenContainerHasSizeNumberOfNotEqualElements() {
        SimpleHashSet<Integer> testSet = new SimpleHashSet<>();
        testSet.add(1);
        testSet.add(1);
        testSet.add(1);
        testSet.add(2);
        testSet.add(3);
        assertThat(testSet.contains(1), is(true));
        assertThat(testSet.contains(2), is(true));
        assertThat(testSet.contains(3), is(true));
        assertThat(testSet.getSize(), is(3));
    }

    /**
     * Test contains.
     */
    @Test
    public void ifAddOneToContainerThenHeContainsOne() {
        SimpleHashSet<Integer> testSet = new SimpleHashSet<>();
        testSet.add(1);
        assertThat(testSet.contains(1), is(true));
        assertThat(testSet.contains(2), is(false));
    }

    @Test
    public void ifRemoveNumberFromContainerThenHeNotContainThisNumber() {
        SimpleHashSet<Integer> testSet = new SimpleHashSet<>();
        testSet.add(1);
        testSet.add(2);
        boolean removeResult = testSet.remove(1);
        boolean falseRemoveResult = testSet.remove(3);
        assertThat(testSet.contains(2), is(true));
        assertThat(testSet.contains(1), is(false));
        assertThat(removeResult, is(true));
        assertThat(falseRemoveResult, is(false));
    }

    /**
     * Test ensureCapacity.
     */
    @Test
    public void ifAddNumberElementsAboveCollectionLengthThenLengthWasIncrease() {
        SimpleHashSet<Integer> testSet = new SimpleHashSet<>(10);
        for (int i = 0; i < 1000; i++) {
            testSet.add(i);
        }
        assertThat(testSet.contains(999), is(true));
        assertThat(testSet.contains(1000), is(false));
        assertThat(testSet.getSize() > 999, is(true));
    }

    /**
     * Test iterator for NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void ifNoElementThenNoSuchElementException() {
        SimpleHashSet<Integer> testSet = new SimpleHashSet<>(10);
        Iterator<Integer> testIterator = testSet.iterator();
        assertThat(testIterator.hasNext(), is(false));
        testIterator.next();
    }

    /**
     * Test iterator for ConcurrentModificationException.
     */
    @Test(expected = ConcurrentModificationException.class)
    public void ifModificationCollectionAfterCreateIteratorThenThrowException() {
        SimpleHashSet<Integer> testSet = new SimpleHashSet<>();
        Iterator<Integer> testIterator = testSet.iterator();
        testSet.add(1);
        testIterator.next();
    }

    /**
     * Test iterator.
     */
    @Test
    public void ifCollectionsHasTwoElementsThenIteratorCanReturnTwoElements() {
        SimpleHashSet<Integer> testSet = new SimpleHashSet<>();
        testSet.add(1);
        testSet.add(2);
        Iterator<Integer> testIterator = testSet.iterator();
        assertThat(testIterator.hasNext(), is(true));
        testIterator.next();
        assertThat(testIterator.hasNext(), is(true));
        testIterator.next();
        assertThat(testIterator.hasNext(), is(false));
    }
}