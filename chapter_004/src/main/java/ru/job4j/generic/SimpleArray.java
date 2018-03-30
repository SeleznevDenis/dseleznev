package ru.job4j.generic;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Простое хранилище данных на основе массива.
 * @author Denis Seleznev
 * @version $Id$
 * @since 30.03.2018
 * @param <T> Тип данных, которые будут с которыми будет работать хранилище.
 */
public class SimpleArray<T> implements Iterable<T> {

    private Object[] array;
    private int index;

    /**
     * Конструктор, инициализирует хранилище, размером 10.
     */
    public SimpleArray() {
        array = new Object[10];
    }

    /**
     * Конструктор, инициализирует хранилище, заданным размером.
     * @param size заданный размер хранилища.
     */
    public SimpleArray(int size) {
        array = new Object[size];
    }

    /**
     * @return количество элементов в хранилище.
     */
    public int size() {
        return index;
    }

    /**
     * Проверяет заполнение хранилища, и если заполнение
     * больше 75% увеличивает размер хранилища в 1.5 раза.
     */
    private void checkIncreaseCapacity() {
        double k = 0.75D;
        if (index > array.length * k) {
            this.array = Arrays.copyOf(this.array, (int) (this.index * 1.5));
        }
    }

    /**
     * Добавляет элемент в хранилище, и при
     * необходимости увеличивает размер хранилища.
     * @param model объект, который нужно добавить в хранилище.
     */
    public void add(T model) {
        checkIncreaseCapacity();
        this.array[index++] = model;
    }

    /**
     * Заменяет объект.
     * @param index индекс заменяемого объекта.
     * @param model объект, который помещаем в
     *              хранилище на место заменяемого объекта.
     */
    public void set(int index, T model) {
        this.array[index] = model;
    }

    /**
     * Удаляет элемент из хранилища со сдвигом.
     * @param index индекс удаляемого элемента.
     */
    public void delete(int index) {
        this.index--;
        System.arraycopy(this.array, index + 1, this.array, index, this.array.length - index - 1);
    }

    /**
     * Возвращает объект из хранилища.
     * @param index индекс требуемого объекта
     * @return объект, под заданным индексом.
     */
    public T get(int index) {
        return (T) this.array[index];
    }

    /**
     * @return итератор типа <T> для обхода хранилища.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int position = 0;

            /**
             * Проверяет наличие элементов после указателя.
             * @return true - если после указателя есть элементы.
             *         false - если после указателя элементов нет.
             */
            @Override
            public boolean hasNext() {
                return this.position < size();
            }

            /**
             * Возвращает текущий элемент и переводит указатель.
             * @return текущий элемент под указателем.
             */
            @Override
            public T next() {
                if (this.position >= size()) {
                    throw new NoSuchElementException();
                }
                return get(position++);
            }
        };
    }
}
