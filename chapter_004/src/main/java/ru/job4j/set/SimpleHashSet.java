package ru.job4j.set;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SimpleHashSet на базе массива.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 04.04.2018
 * @param <E> Параметризованный тип контейнера.
 */
public class SimpleHashSet<E> implements Iterable<E> {

    /**
     * Главное хранилище.
     */
    private Object[] mainStorage;

    /**
     * Константа - коэффициент загрузки хранилища.
     */
    private static final double LOAD_FACTOR = 0.5;

    /**
     * Константа - коэффициент увеличения хранилища.
     */
    private static final int MAGNIFICATION_FACTOR = 2;

    /**
     * Количество хранимых элементов в коллекции.
     */
    private int size;

    /**
     * Счетчик операций, которые могут повлиять на правильную работу итератора.
     */
    private int modCount;

    /**
     * Конструктор, инициализирует хранилище размером 20.
     */
    public SimpleHashSet() {
        this.mainStorage =  new Object[20];
    }

    /**
     * Конструктор, инициализирует хранилище заданным размером.
     * @param size - заданный размер.
     */
    public SimpleHashSet(int size) {
        this.mainStorage = new Object[size];
    }

    /**
     * Возвращает количество хранимых в коллекции элементов.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Производит добавление элемента в хранилище.
     * @param element добавляемый элемент.
     * @return true - если хранилище не содержит добавляемого элемента.
     *         false - если хранилище уже содержит добавляемый элемент.
     */
    public boolean add(E element) {
        boolean result = false;
        if (this.mainStorage[getHashModule(element)] == null
                || !this.mainStorage[getHashModule(element)].equals(element)) {
            result = false;
            while (!result) {
                if (LOAD_FACTOR < this.size / this.mainStorage.length
                        || this.mainStorage[getHashModule(element)] != null) {
                    increaseCapacity();
                } else {
                    this.mainStorage[getHashModule(element)] = element;
                    this.size++;
                    result = true;
                }
            }
            this.modCount++;
        }
        return result;
    }

    /**
     * Проверяет, содержит ли коллекция заданный элемент.
     * @param element заданный для проверки элемент.
     * @return true - если содержит.
     *         false - если не содержит.
     */
    public boolean contains(E element) {
        boolean result = false;
        if (this.mainStorage[getHashModule(element)] != null
                && this.mainStorage[getHashModule(element)].equals(element)) {
            result = true;
        }
        return result;
    }

    /**
     * Удаляет элемент из хранилища.
     * @param element заданный для удаления элемент.
     * @return true - если элемент удален из коллекции.
     *         false - если элемент не найден в коллекции.
     */
    public boolean remove(E element) {
        boolean result = false;
        int elementHashModule = getHashModule(element);
        if (this.mainStorage[elementHashModule] != null) {
            this.mainStorage[elementHashModule] = null;
            result = true;
            this.modCount++;
            this.size--;
        }
        return result;
    }

    /**
     * Вычисляет хэш модуль элемента, в зависимости от размеров хэш таблицы.
     * @param element заданный для вычисления хэш модуля элемент.
     * @return хэш модуль заданного элемента.
     */
    private int getHashModule(E element) {
        return element.hashCode() % this.mainStorage.length;
    }

    /**
     * Вычисляет хэш модуль элемента.
     * @param element заданный для вычисления хэш модуля элемент.
     * @param newTableSize размер хэш таблицы, для вычисления хэш модуля.
     * @return хэш модуль заданного элемента.
     */
    private int getHashModule(E element, int newTableSize) {
        return element.hashCode() % newTableSize;
    }

    /**
     * Увеличивает размер таблицы и производит перехеширование
     * всех элементов, в соответствии с новым размером.
     */
    private void increaseCapacity() {
        Object[] newStorage = new Object[this.mainStorage.length * MAGNIFICATION_FACTOR];
        for (Object element : this.mainStorage) {
            newStorage[getHashModule((E) element, newStorage.length)] = element;
        }
        this.mainStorage = newStorage;
    }

    /**
     * @return возвращает итератор для обхода коллекции.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            /**
             * Указатель для обхода коллекции.
             */
            private int position;

            /**
             * Число изменений коллекции, на момент создания итератора.
             */
            private final int expectedModCount = modCount;

            /**
             * Статический блок, инициализрует указатель position.
             */
            {
                position = -1;
                position = findNextNotNullElement();
            }

            /**
             * @return возвращает индекс следующего ненулевого элемента.
             */
            private int findNextNotNullElement() {
                int result = -1;
                for (int i = position + 1; i < mainStorage.length; i++) {
                    if (mainStorage[i] != null) {
                        result = i;
                        break;
                    }
                }
                return result;
            }

            /**
             * @return true - если за указателем есть ненулевой элемент.
             *         false - если ненулевых элементов больше нет.
             */
            @Override
            public boolean hasNext() {
                return (this.position != -1);
            }

            /**
             * Возвращает элемент под текущим указателем и
             * переводит указатель на следующий ненулевой элемент.
             * @return элемент, под текущим указателем.
             */
            @Override
            public E next() {
                if (this.expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E result = (E) mainStorage[this.position];
                this.position = findNextNotNullElement();
                return result;
            }
        };
    }
}