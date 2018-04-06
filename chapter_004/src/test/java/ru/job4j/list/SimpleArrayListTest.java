package ru.job4j.list;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test SimpleArrayList.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 31.03.2018
 */
public class SimpleArrayListTest {

    /**
     * Test add.
     */
    @Test
    public void ifAddIntegerToTestListThenListHasThisInteger() {
        SimpleList<Integer> testList = new SimpleArrayList<>();
        testList.add(8);
        testList.add(10);
        assertThat(testList.get(0), is(8));
        assertThat(testList.get(1), is(10));
    }

    /**
     * Test iterator.
     */
    @Test
    public void hasNextNextSequentialInvocation() {
        SimpleArrayList<Integer> testArray = new SimpleArrayList<>();
        testArray.add(1);
        testArray.add(2);
        testArray.add(3);
        Iterator<Integer> it = testArray.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(false));
    }

    /**
     * Test concurrentModificationException in iterator.
     */
    @Test(expected = ConcurrentModificationException.class)
    public void ifCollectionWasModificationThenThrowConcurrentModificationException() {
        SimpleArrayList<Integer> testArray = new SimpleArrayList<>();
        Iterator<Integer> it = testArray.iterator();
        testArray.add(1);
        it.next();
    }
}