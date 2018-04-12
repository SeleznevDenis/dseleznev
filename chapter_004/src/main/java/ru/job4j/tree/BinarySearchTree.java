package ru.job4j.tree;

import java.util.*;

/**
 * Простое бинарное дерево поиска.
 * @author Denis Seleznev
 * @version $Id$
 * @since 12.04.2018
 * @param <E> Параметризованный тип, ограничен классами реализующими интерфейс Comparable.
 */
public class BinarySearchTree<E extends Comparable<E>> implements Iterable<E> {
    /**
     * Корень дерева.
     */
    private Node<E> root;

    /**
     * Счетчик изменений, которые могут повлиять на правильную работу итератора.
     */
    private int modCount;

    /**
     * Добавляет элемент в дерево, если дерево пустое.
     * Иначе вызывает метод рекурсивного поиска и добавления по дереву.
     * @param element добавляемый элемент.
     */
    public void add(E element) {
        if (this.root == null) {
            this.root = new Node<>(element, null);
        } else {
            recursiveAdd(element, this.root);
        }
        this.modCount++;
    }

    /**
     * Рекурсивно проводит поиск по дереву и добавляет элемент.
     * @param element добавляемый элемент.
     * @param parent стартовый узел для поиска.
     */
    private void recursiveAdd(E element, Node<E> parent) {
        if (element.compareTo(parent.getValue()) > 0) {
            if (parent.right != null) {
                recursiveAdd(element, parent.right);
            } else {
                parent.right = new Node<>(element, parent);
            }
        } else {
            if (parent.left != null) {
                recursiveAdd(element, parent.left);
            } else {
                parent.left = new Node<>(element, parent);
            }
        }
    }

    /**
     * Итератор, обходит коллекцию методом in-order.
     * @return итератор для обхода коллекции
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            /**
             * Ожидаемое количество изменений влияющих на работу итератора.
             */
            int expectedModCount = modCount;

            /**
             * Указатель для обхода коллекции.
             */
            Node<E> next;

            {
                this.next = root;
                if (next != null) {
                    while (next.left != null) {
                        next = next.left;
                    }
                }
            }

            /**
             * Проверяет наличие следующего элемента для вывода.
             * @return true - если элемент есть.
             *         false - если элементов больше нет.
             */
            @Override
            public boolean hasNext() {
                return next != null;
            }

            /**
             * Выводит текущий элемент для вывода, и сдвигает указатель дальше.
             * @return значение, текущего узла для вывода.
             */
            @Override
            public E next() {
                Node<E> currentNode = next;
                if (this.expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (currentNode == null || currentNode.value == null) {
                    throw new NoSuchElementException();
                }
                this.next = findNextNode(currentNode);
                return currentNode.getValue();
            }

            /**
             * Производит поиск следующего элемента для вывода.
             * Метод обхода in-order.
             * @param currentNode текущий узел.
             * @return следующий узел.
             */
            private Node<E> findNextNode(Node<E> currentNode) {
                Node<E> foundNode = null;
                if (currentNode != null) {
                    if (currentNode.right != null) {
                        foundNode = currentNode.right;
                        while (foundNode.left != null) {
                            foundNode = foundNode.left;
                        }
                    } else {
                        Node<E> parent = currentNode.parent;
                        Node<E> child = currentNode;
                        while (parent != null && child == parent.right) {
                            child = parent;
                            parent = parent.parent;
                        }
                        foundNode = parent;
                    }
                }
                return foundNode;
            }
        };
    }

    /**
     * Клас описывает узлы дерева.
     * @param <E> Параметризованный тип значения, хранящегося в узле.
     */
    private static class Node<E> {
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        E value;

        Node(E value, Node<E> parent) {
            this.value = value;
            this.parent = parent;
        }

        public E getValue() {
            return this.value;
        }
    }
}
