package ru.job4j.list;

import ru.job4j.generic.SimpleArray;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Структура данных на основе двунаправленного связного списка.
 * @author Denis Seleznev
 * @version $Id$
 * @since 01.04.2018
 * @param <E> Параметризованный тип списка.
 */
public class SimpleLinkedList<E> implements SimpleList<E> {

    private int size;
    private Node<E> first;
    private Node<E> last;
    private int modCount;

    /**
     * @return размер списка.
     */
    public int getSize() {
        return size;
    }

    /**
     * Вводит объект в хранилище.
     * @param value объект, который необходимо добавить в хранилище.
     */
    @Override
    public void add(E value) {
        this.modCount++;
        Node<E> newNode = new Node<>(this.last, value, null);
        if (size == 0) {
            this.first = newNode;
        } else {
            this.last.next = newNode;
        }
        this.last = newNode;
        size++;
    }

    /**
     * Возвращает ссылку на объект из хранилища по заданному индексу.
     * @param index заданный индекс.
     * @return ссылка на запрашиваемый объект.
     */
    @Override
    public E get(int index) {
        return findNodeByIndex(index).item;
    }

    /**
     * Удаляет первый объект из списка.
     * @return удаляемый объект.
     */
    public E pollFirst() {
        E result = this.first.item;
        if (size > 1) {
            this.first.next.prev = null;
            this.first = this.first.next;
        } else {
            this.first = null;
            this.last = null;
        }
        size--;
        modCount++;
        return result;
    }

    /**
     * Удаляет последний объект из списка.
     * @return удаляемый объект.
     */
    public E pollLast() {
        E result = this.last.item;
        if (size > 1) {
            this.last.prev.next = null;
            this.last = this.last.prev;
        } else {
            this.first = null;
            this.last = null;
        }
        size--;
        modCount++;
        return result;
    }

    /**
     * Производит поиск узла по индексу.
     * @param index индекс узла.
     * @return искомый узел.
     */
    private Node<E> findNodeByIndex(int index) {
        int halfList = this.size / 2;
        Node<E> currentNode;
        if (index <= halfList) {
            currentNode = this.first;
            for (int nodeCount = 0; nodeCount < index; nodeCount++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = this.last;
            for (int nodeCount = size - 1; nodeCount > index; nodeCount--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    /**
     * @return возвращает итератор для обхода списка.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int nodeCount;
            private final int expectedModCount = modCount;
            private Node<E> currentNode = first;

            /**
             * Проверяет наличие объектов за указателем.
             * @return true - если объект есть.
             *         false - если объектов больше нет.
             */
            @Override
            public boolean hasNext() {
                return this.nodeCount < size;
            }

            /**
             * Возвращает текущий объект и переводит указатель.
             * @return ссылка на текущий объект под указателем.
             */
            @Override
            public E next() {
                if (this.expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (this.nodeCount >= size) {
                    throw new NoSuchElementException();
                }
                E result = this.currentNode.item;
                this.currentNode = this.currentNode.next;
                this.nodeCount++;
                return result;
            }
        };
    }

    /**
     * Внутренний класс, описывающий объекты Node, которые будут
     * храниться в главном хранилище и обеспечивать работу двунаправленного
     * связного списка. У объектов есть поля:
     * item - хранимый в объекте элемент.
     * next - ссылка на следующий Node.
     * prev - ссылка на предыдущий Node.
     * @param <E> Параметризованный тип Node.
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        /**
         * Конструктор, инициализирует поля:
         * @param prev ссылка предыдущий элемент.
         * @param element элемент хранящийся в node.
         * @param next ссылка на следующий элемент.
         */
        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
