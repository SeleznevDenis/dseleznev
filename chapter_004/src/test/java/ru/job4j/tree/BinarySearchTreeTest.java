package ru.job4j.tree;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Test BinarySearchTree
 * @author Denis Seleznev
 * @version $Id$
 * @since 12.04.2018
 */
public class BinarySearchTreeTest {

    @Test
    public void ifAddElementsToTreeThenIteratorShouldReturnThenByNaturalOrderSort() {
        BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
        testTree.add(8);
        testTree.add(12);
        testTree.add(4);
        testTree.add(14);
        testTree.add(10);
        testTree.add(6);
        testTree.add(2);
        Iterator<Integer> testIterator = testTree.iterator();
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(2));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(4));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(6));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(8));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(10));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(12));
        assertThat(testIterator.hasNext(), is(true));
        assertThat(testIterator.next(), is(14));
        assertThat(testIterator.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void ifNoSuchElementThenIteratorThroeNSEException() {
        BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
        testTree.add(6);
        Iterator<Integer> testIterator = testTree.iterator();
        assertThat(testIterator.next(), is(6));
        testIterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void ifModCollectionAfterIteratorCreateThenHeThrowException() {
        BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
        Iterator<Integer> testIterator = testTree.iterator();
        testTree.add(1);
        testIterator.next();
    }
}