package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 01.04.2018
 */
public class SimpleStackTest {

    /**
     * Test LIFO
     */
    @Test
    public void ifLastInputThenFirstOutput() {
        SimpleStack<Integer> testStack = new SimpleStack<>();
        testStack.push(1);
        testStack.push(2);
        testStack.push(3);
        assertThat(testStack.poll(), is(3));
        assertThat(testStack.poll(), is(2));
        assertThat(testStack.poll(), is(1));
    }
}