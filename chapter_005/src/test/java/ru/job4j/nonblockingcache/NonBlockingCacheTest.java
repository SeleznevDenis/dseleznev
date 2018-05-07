package ru.job4j.nonblockingcache;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;

/**
 * Test NonBlockingCache.
 * @author Denis Seleznev
 * @version $Id$
 * @since 07.05.2018
 */
public class NonBlockingCacheTest {

    private NonBlockingCache testCache;

    @Before
    public void setUp() {
        this.testCache = new NonBlockingCache();
    }

    @Test
    public void ifAddTaskThenCacheHaveTask() {
        Boolean result = true;
        for (int i = 0; i < 10; i++) {
            result &= testCache.add(new Task(i, Integer.toString(i)));
        }
        assertThat(result, is(true));
        assertThat(testCache.getById(9).getName(), is("9"));
    }

    @Test
    public void ifDeleteTaskThenCacheHasNotTask() {
        testCache.add(new Task(1, "10"));
        Boolean result = testCache.delete(1);
        assertThat(result, is(true));
        assertNull(testCache.getById(1));
    }

    @Test(expected = OptimisticException.class)
    public void ifVersionsOfTasksAreDifferenceThenThrowOE() {
        testCache.add(new Task(1, "10"));
        testCache.update(1, new Task(1, "11"));
        testCache.update(1, new Task(1, "12"));
    }

    @Test
    public void ifUpdateTaskThenTaskHasNewNameAndVersion() {
        testCache.add(new Task(1, "10"));
        testCache.update(1, new Task(1, "11"));
        assertThat(testCache.getById(1).getVersion(), is(1));
        assertThat(testCache.getById(1).getName(), is("11"));
    }
}