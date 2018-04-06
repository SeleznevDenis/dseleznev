package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 01.04.2018
 */
public class SimpleQueueTest {

    /**
     * Test FIFO.
     */
    @Test
    public void ifLastInputThenLastOutput() {
        SimpleQueue<Integer> testQueue = new SimpleQueue<>();
        testQueue.push(1);
        testQueue.push(2);
        testQueue.push(3);
        assertThat(testQueue.poll(), is(1));
        assertThat(testQueue.poll(), is(2));
        assertThat(testQueue.poll(), is(3));
    }
}