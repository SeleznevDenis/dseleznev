package ru.job4j.generic;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test SimpleArray
 * @author Denis Seleznev
 * @version $Id$
 * @since 30.03.2018
 */
public class SimpleArrayTest {

    /**
     * Test add.
     */
    @Test
    public void ifAddIntegerTwoInSimpleArrayThenGetZeroWasTwo() {
        SimpleArray<Integer> testArray = new SimpleArray<>();
        testArray.add(2);
        assertThat(testArray.get(testArray.size() - 1), is(2));
    }

    /**
     * Test set.
     */
    @Test
    public void ifAddTwoThenSetToThreeThenLastElementWasThree() {
        SimpleArray<Integer> testArray = new SimpleArray<>();
        testArray.add(2);
        testArray.set(0, 3);
        assertThat(testArray.get(testArray.size() - 1), is(3));
    }

    /**
     * Test delete.
     */
    @Test
    public void ifDeleteElementOfIndexTwoThenElementOfIndexThreeWillTakeIndexTwo() {
        SimpleArray<Integer> testArray = new SimpleArray<>();
        testArray.add(1);
        testArray.add(2);
        testArray.add(3);
        testArray.delete(1);
        assertThat(testArray.get(1), is(3));
    }

    /**
     * Test get.
     */
    @Test
    public void ifAddElementThenGetIndexZeroWillReturnElement() {
        SimpleArray<Integer> testArray = new SimpleArray<>();
        testArray.add(500);
        assertThat(testArray.get(testArray.size() - 1), is(500));
    }

    /**
     * Test iterator.
     */
    @Test
    public void iterator() {
        SimpleArray<Integer> testArray = new SimpleArray<>();
        Iterator<Integer> it = testArray.iterator();
        testArray.add(1);
        testArray.add(2);
        testArray.add(3);
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(false));
    }
}