package ru.job4j.list;

/**
 * Узел с однонаправленной связью.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 02.04.2018
 * @param <T> Параметризованный тип.
 */
public class Node<T> {

    private T value;
    private Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public T getValue() {
        return this.value;
    }
}