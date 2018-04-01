package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 01.04.2018
 */
public class SimpleLinkedListTest {

    /**
     * Test add and get.
     */
    @Test
    public void addAndGetElementsFromSimpleLinkedListTest() {
        SimpleList<Integer> testList = new SimpleLinkedList<>();
        testList.add(45);
        testList.add(46);
        testList.add(47);
        testList.add(48);
        testList.add(49);
        assertThat(testList.get(0), is(45));
        assertThat(testList.get(1), is(46));
        assertThat(testList.get(2), is(47));
        assertThat(testList.get(3), is(48));
        assertThat(testList.get(4), is(49));
    }

    /**
     * Test iterator.
     */
    @Test
    public void shouldReturnAllNumbersSequentially() {
        SimpleList<Integer> testList = new SimpleLinkedList<>();
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);
        testList.add(5);
        Iterator<Integer> testIterator = testList.iterator();
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(1));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(2));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(3));
        assertThat(testIterator.next(), is(4));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(5));
        assertThat(testIterator.hasNext(), is(false));
    }

    /**
     * Test iterator's NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void ifHasNoNextElementShouldReturnNoSuchElementException() {
        SimpleList<Integer> testList = new SimpleLinkedList<>();
        testList.add(5);
        Iterator<Integer> testIterator = testList.iterator();
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(5));
        testIterator.next();
    }

    /**
     * Test iterator's ConcurrentModificationException.
     */
    @Test(expected = ConcurrentModificationException.class)
    public void ifModificationCollectionAfterIteratorAdditionThenReturnException() {
        SimpleList<Integer> testList = new SimpleLinkedList<>();
        Iterator<Integer> testIterator = testList.iterator();
        testList.add(1);
        testIterator.next();
    }

    /**
     * Test iterator with void SimpleList.
     */
    @Test
    public void ifCollectionAreVoidThenHasNextWillReturnFalse() {
        SimpleList<Integer> testList = new SimpleLinkedList<>();
        Iterator<Integer> testIterator = testList.iterator();
        assertThat(testIterator.hasNext(), is(false));
    }

    /**
     * Test ensure capacity.
     */
    @Test
    public void ifAddOneHundredElementsToListThenLastElementWasNineNine() {
        SimpleList<Integer> testList = new SimpleLinkedList<>();
        for (int i = 0; i < 100; i++) {
            testList.add(i);
        }
        assertThat(testList.get(99), is(99));
    }

    /**
     * Test deletion first element.
     */
    @Test
    public void ifDeleteFirstElementThenSecondElementTakesHimPlace() {
        SimpleLinkedList<Integer> testList = new SimpleLinkedList<>();
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.pollFirst();
        Iterator<Integer> testIterator = testList.iterator();
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(2));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(3));
        assertThat(testIterator.hasNext(), is(false));
    }

    /**
     * Test deletion last element.
     */
    @Test
    public void ifDeleteLastElementThenOrderOfElementsWillBePreserved() {
        SimpleLinkedList<Integer> testList = new SimpleLinkedList<>();
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.pollLast();
        Iterator<Integer> testIterator = testList.iterator();
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(1));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(2));
        assertThat(testIterator.hasNext(), is(false));
    }
}

