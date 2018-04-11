package ru.job4j.tree;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 10.04.2018
 */
public class TreeTest {
    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }
    @Test
    public void ifAddElementsToTreeThenIteratorReturnElements() {
        Tree<Integer> testTree = new Tree<>(1);
        testTree.add(1, 2);
        testTree.add(1, 3);
        testTree.add(2, 4);
        testTree.add(2, 5);
        testTree.add(3, 6);
        testTree.add(3, 7);
        Iterator<Integer> testIterator = testTree.iterator();
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(2));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(4));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(5));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(3));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(6));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(7));
        assertThat(testIterator.hasNext(), is(false));
    }
    @Test
    public void ifTreeBinaryThenIsBinaryReturnTrue() {
        Tree<Integer> testTree = new Tree<>(1);
        testTree.add(1, 2);
        testTree.add(1, 3);
        testTree.add(2, 4);
        testTree.add(3, 5);
        testTree.add(3, 6);
        assertThat(testTree.isBinary(), is(true));
    }
    @Test
    public void ifTreeNotBinaryThenIsBinaryReturnFalse() {
        Tree<Integer> testTree = new Tree<>(1);
        testTree.add(1, 2);
        testTree.add(1, 3);
        testTree.add(2, 4);
        testTree.add(2, 5);
        testTree.add(3, 6);
        testTree.add(3, 7);
        testTree.add(3, 8);
        assertThat(testTree.isBinary(), is(false));
    }
}
