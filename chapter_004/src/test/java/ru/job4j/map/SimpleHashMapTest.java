package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test SimpleHashSet.
 * @author Denis Seleznev
 * @version $Id$
 * @since 08.04.2018
 */
public class SimpleHashMapTest {

    SimpleHashMap<User, String> testMap;

    @Before
    public void setUp() {
        this.testMap = new SimpleHashMap<>();
    }

    /**
     * Test getSize.
     */
    @Test
    public void ifInsertTwoElementsToMapThenMapGetSizeWasTwo() {
        testMap.insert(new User("Oleg", 1, new GregorianCalendar(20, 1, 1987)), "Student");
        testMap.insert(new User("Ivan", 2, new GregorianCalendar(10, 6, 1980)), "Teacher");
        assertThat(testMap.getSize(), is(2));
    }

    /**
     * Test insert.
     */
    @Test
    public void ifInsertDuplicateElementsToMapThenMapHasOneElement() {
        boolean insertResult = testMap.insert(
                new User("Oleg", 1, new GregorianCalendar(20, 1, 1987)), "Student"
        );
        boolean insertDuplicateResult = testMap.insert(
                new User("Oleg", 1, new GregorianCalendar(20, 1, 1987)), "Student"
        );
        Iterator<SimpleHashMap.Entry<User, String>> testIterator = testMap.iterator();
       // assertThat(insertResult, is(true));
       // assertThat(insertDuplicateResult, is(false));
        assertThat(testIterator.next().getValue(), is("Student"));
        assertThat(testIterator.hasNext(), is(false));
    }

    /**
     * Test delete.
     */
    @Test
    public void ifDeleteItemThenMapHasNoItem() {
        testMap.insert(new User("Oleg", 1, new GregorianCalendar(20, 1, 1987)), "Student");
        boolean deleteResult = testMap.delete(new User("Oleg", 1, new GregorianCalendar(20, 1, 1987)));
        boolean deleteNoItemResult = testMap.delete(new User("Oleg", 1, new GregorianCalendar(20, 1, 1987)));
        assertThat(deleteResult, is(true));
        assertThat(deleteNoItemResult, is(false));
        assertThat(testMap.getSize(), is(0));
    }

    /**
     * Test iterator with NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void ifMapIsEmptyThenIteratorNextThrowNoSuchElementException() {
        Iterator<SimpleHashMap.Entry<User, String>> testIterator = testMap.iterator();
        assertThat(testIterator.hasNext(), is(false));
        testIterator.next();
    }

    /**
     * Test iterator with ConcurrentModificationException.
     */
    @Test(expected = ConcurrentModificationException.class)
    public void ifModificationMapAfterIteratorCreateThenThrowException() {
        Iterator<SimpleHashMap.Entry<User, String>> testIterator = testMap.iterator();
        testMap.insert(new User("Oleg", 1, new GregorianCalendar(20, 1, 1987)), "Student");
        testIterator.next();
    }

    /**
     * Test ensureCapacity.
     */
    @Test
    public void ifAddToMapMoreElementsThenCapacityWasIncrease() {
        SimpleHashMap<Integer, Integer> testIntegerMap = new SimpleHashMap<>(10);
        for (int i = 0; i < 100; i++) {
            testIntegerMap.insert(i, i);
        }
        assertThat(testIntegerMap.getSize() > 10, is(true));
    }

    /**
     * Test get.
     */
    @Test
    public void ifAddElementToMapThenIfGetKeyWillReturnValueOfElement() {
        testMap.insert(new User("Oleg", 1, new GregorianCalendar(20, 1, 1987)), "Student");
        String result = testMap.get(new User("Oleg", 1, new GregorianCalendar(20, 1, 1987)));
        assertThat(result, is("Student"));
    }
}