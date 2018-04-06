package ru.job4j.set;

import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;

/**
 * Простой Set на базе SimpleArraySet.
 * @author Denis Seleznev
 * @version $Id$
 * @since 02.04.2018
 * @param <E> Параметризованный тип.
 */
public class SimpleSet<E> implements Iterable<E> {
    /**
     * Главное хранилище.
     */
    private SimpleArrayList<E> mainStorage;

    /**
     * Конструктор, инициализирует хранилище.
     */
    public SimpleSet() {
        this.mainStorage = new SimpleArrayList<>();
    }

    /**
     * Конструктор, инициализирует хранилище заданным размером.
     * @param size заданный размер.
     */
    public SimpleSet(int size) {
        this.mainStorage = new SimpleArrayList<>(size);
    }

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
     * Возвращает итератор для обхода коллекции.
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return this.mainStorage.iterator();
    }
}