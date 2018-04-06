package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test CycleCheck.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 02.04.2018
 */
public class CycleCheckTest {

    private Node<Integer> first;
    private Node<Integer> two;
    private Node<Integer> third;
    private Node<Integer> four;

    @Before
    public void setUp() {
        first = new Node<>(1);
        two = new Node<>(2);
        third = new Node<>(3);
        four = new Node<>(4);
    }

    /**
     * Test если список содержит замыкание.
     */
    @Test
    public void ifListHasCycle() {
        first.setNext(two);
        two.setNext(third);
        third.setNext(four);
        four.setNext(first);
        assertThat(new CycleCheck().hasCycle(first), is(true));
    }

    /**
     * Test если список не содержит замыкание.
     */
    @Test
    public void ifListHasNotCycle() {
        first.setNext(two);
        two.setNext(third);
        third.setNext(four);
        four.setNext(null);
        assertThat(new CycleCheck().hasCycle(first), is(false));
    }

    /**
     * Test если список замыкается по середине.
     */
    @Test
    public void ifListHasMediumCycle() {
        first.setNext(two);
        two.setNext(third);
        third.setNext(two);
        four.setNext(null);
        assertThat(new CycleCheck().hasCycle(first), is(true));
    }
}