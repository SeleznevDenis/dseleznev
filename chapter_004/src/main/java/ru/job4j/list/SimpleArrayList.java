package ru.job4j.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Динамический список на базе массива.
 * @author Denis Seleznev
 * @version $Id$
 * @since 31.03.2018
 * @param <E> Параметризованный тип данных, хранящихся в списке.
 */
public class SimpleArrayList<E> implements SimpleList<E> {

    private Object[] array;
    private int index;
    private static final double FILL_FACTOR = 0.75D;
    private static final double MAGNIFICATION_FACTOR = 1.5D;
    private int modCount;

    /**
     * Конструктор, инициализирует массив array, заданным размером.
     * @param size заданный размер.
     */
    public SimpleArrayList(int size) {
        this.array = new Object[size];
    }

    /**
     * Конструктор, инициализирует массив array, размером 10.
     */
    public SimpleArrayList() {
        this.array = new Object[10];
    }

    /**
     * Проверяет заполнение массива и при необходимости увеличивает его.
     */
    private void checkEnsureCapacity() {
        if (index > array.length * FILL_FACTOR) {
            this.array = Arrays.copyOf(this.array, (int) (this.index * MAGNIFICATION_FACTOR));
        }
    }

    /**
     * Добавляет элемент в хранилище.
     * @param value заданный для добавления элемент.
     */
    @Override
    public void add(E value) {
        checkEnsureCapacity();
        this.modCount++;
        this.array[index++] = value;
    }

    /**
     * Возвращает элемент из хранилища.
     * @param index индекс требуемого элемента.
     * @return элемент соответствующий индексу.
     */
    @Override
    public E get(int index) {
        return (E) this.array[index];
    }

    /**
     * @return Итератор для обхода списка.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int position;
            private final int expectedModCount = modCount;

            /**
             * Проверяет наличие элементов после текущего.
             * @return true - если элемент есть.
             *         false - если элементов больше нет.
             */
            @Override
            public boolean hasNext() {
                return (this.position < index);
            }

            /**
             * Возвращает текущий элемент и переводит указатель.
             * @return текущий элемент.
             */
            @Override
            public E next() {
                if (this.expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (this.position >= index) {
                    throw new NoSuchElementException();
                }
                return (E) array[position++];
            }
        };
    }
}