package ru.job4j.count;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Префиксное дерево, модифицированное для суммирования индексов слов.
 * @author Denis Seleznev (d.selezneww@mail.ru).
 * @version $Id$
 * @since 21.04.2018
 */
public class Trie {

    /**
     * Корень дерева.
     */
    private Node root = new Node();

    /**
     * Вводит слово в дерево, а также добавляет его индекс.
     *
     * @param string заданное для добавления слово.
     * @param position индекс заданного для добавления слова.
     */
    public void put(String string, Integer position) {
        Node currentNode = this.root;
        for (Character symbol : string.toLowerCase().toCharArray()) {
            if (!currentNode.children.containsKey(symbol)) {
                currentNode.children.put(symbol, new Node());
            }
            currentNode = currentNode.children.get(symbol);
        }
        currentNode.leaf = true;
        if (currentNode.setOfIndices == null) {
            currentNode.setOfIndices = new LinkedHashSet<>();
        }
        currentNode.setOfIndices.add(position);
    }

    /**
     * Находит заданное слово в дереве и возвращает его индексы в тексте.
     *
     * @param string заданное слово.
     * @return Set<Integer> с индексами слова в порядке возрастания, если слово есть.
     *         Иначе возвращает пустой Set<Integer>.
     */
    public Set<Integer> getIndices(String string) {
        Node currentNode = this.root;
        Set<Integer> result = new LinkedHashSet<>();
        for (Character symbol : string.toLowerCase().toCharArray()) {
            if (!currentNode.children.containsKey(symbol)) {
                break;
            } else {
                currentNode = currentNode.children.get(symbol);
            }
        }
        if (currentNode.leaf) {
            result = currentNode.setOfIndices;
        }
        return result;
    }

    /**
     * Класс опписывает узлы префиксного дерева.
     */
    private static class Node {

        /**
         * Переменная описывает, является ли символ концом слова.
         */
        boolean leaf;

        /**
         * Ссылка на множество, которое должно хранить индексы слова, либо пустое множество.
         * Инициализируется множеством, только у последнего символа слова.
         * Остальные узлы содержат ссылку на null.
         */
        Set<Integer> setOfIndices;

        /**
         * Ключ данной карты содержит символ текущего узла.
         * Значение данной карты содержит ссылки на потомков узла.
         */
        HashMap<Character, Node> children = new HashMap<>();
    }
}
