package ru.job4j.list;

/**
 * Очередь LIFO на базе SimpleLinkedList.
 * @author Denis Seleznev
 * @version $Id$
 * @since 01.04.2018
 */
public class SimpleStack<T> {

    private SimpleLinkedList<T> storage = new SimpleLinkedList<>();

    /**
     * Удаляет последний элемент из очереди.
     * @return удаленный элемент из головы очереди.
     */
    public T poll() {
        return storage.pollLast();
    }

    /**
     * Вводит элемент в очередь.
     * @param value элемент.
     */
    public void push(T value) {
        storage.add(value);
    }
}