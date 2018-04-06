package ru.job4j.list;

/**
 * Очередь FIFO на базе SimpleLinkedList
 * @author Denis Seleznev
 * @version $Id$
 * @since 01.04.2018
 */
public class SimpleQueue<T> {

    private SimpleLinkedList<T> storage = new SimpleLinkedList<>();

    /**
     * Удаляет первый элемент из очереди.
     * @return удаленный элемент из головы очереди.
     */
    public T poll() {
        return storage.pollFirst();
    }

    /**
     * Вводит элемент в очередь.
     * @param value элемент.
     */
    public void push(T value) {
        storage.add(value);
    }
}