package ru.job4j.set;

import ru.job4j.list.SimpleLinkedList;

import java.util.Iterator;

/**
 * SimpleLinkedSet на базе SimpleLinkedList.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 02.04.2018
 * @param <E> Парамтеризованный тип.
 */
public class SimpleLinkedSet<E> implements Iterable<E> {

    /**
     * Главное хранилище.
     */
    private SimpleLinkedList<E> mainStorage = new SimpleLinkedList<>();

    /**
     * Производит добавление элемента в хранилище.
     * Соблюдается условие, что хранятся только разные элементы.
     * @param element вводимый в хранилище элемент.
     * @return true - если добавление прошло успешно.
     *         false - если добавление не удалось.
     */
    public boolean add(E element) {
        for (E checkedElement : this.mainStorage) {
            if (checkedElement.equals(element)) {
                return false;
            }
        }
        this.mainStorage.add(element);
        return true;
    }

    /**
     * @return итератор для обхода коллекции.
     */
    @Override
    public Iterator<E> iterator() {
        return this.mainStorage.iterator();
    }
}