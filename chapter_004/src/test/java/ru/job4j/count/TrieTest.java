package ru.job4j.count;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test Trie.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 21.04.2018
 */
public class TrieTest {

    @Test
    public void ifPutWorldInTrieThenGetIndicesShouldReturnAllIndices() {
        Trie testTrie = new Trie();
        testTrie.put("hello", 1);
        testTrie.put("hellBoy", 2);
        testTrie.put("hello", 3);
        testTrie.put("world", 4);
        Set<Integer> expectHello = new LinkedHashSet<>();
        expectHello.add(1);
        expectHello.add(3);
        Set<Integer> expectWorld = new LinkedHashSet<>();
        expectWorld.add(4);
        assertThat(testTrie.getIndices("Hello"), is(expectHello));
        assertThat(testTrie.getIndices("world"), is(expectWorld));
    }

    @Test
    public void ifTriesNotContainsWorldThenReturnEmptySet() {
        Trie testTrie = new Trie();
        testTrie.put("hello", 1);
        assertThat(testTrie.getIndices("world"), is(new LinkedHashSet<>()));
    }

    @Test
    public void ifTriesNotContainsWorldButContainsPrefixShouldReturnEmptySet() {
        Trie testTrie = new Trie();
        testTrie.put("hello", 1);
        assertThat(testTrie.getIndices("he"), is(new LinkedHashSet<>()));
    }
}