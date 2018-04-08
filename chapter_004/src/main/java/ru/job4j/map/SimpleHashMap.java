package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SimpleHashMap
 * @author Denis Seleznev
 * @version $Id$
 * @since 08.04.2018
 * @param <K> Параметризованный тип ключа.
 * @param <V> Параметризованный тип значения.
 */
public class SimpleHashMap<K, V> implements Iterable<SimpleHashMap.Entry<K, V>> {

    /**
     * Главное хранилище.
     */
    private Entry<K, V>[] mainStorage;

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
    public SimpleHashMap() {
        this.mainStorage =  (Entry<K, V>[]) new Entry[20];
    }

    /**
     * Конструктор, инициализирует хранилище заданным размером.
     * @param size - заданный размер.
     */
    public SimpleHashMap(int size) {
        this.mainStorage = (Entry<K, V>[]) new Entry[size];
    }

    /**
     * Возвращает количество хранимых в коллекции пар.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Производит добавление пары ключ - значение в хранилище.
     * @param key ключ.
     * @param value значение.
     * @return true - если хранилище не содержит добавляемого ключа и если не возникло коллизии.
     *         false - если хранилище уже содержит добавляемый ключ либо если возникла коллизия.
     */
    public boolean insert(K key, V value) {
        boolean result = false;
        if (this.mainStorage[getHashModule(key)] == null) {
            if (LOAD_FACTOR < (this.size / (double) this.mainStorage.length)) {
                increaseCapacity();
            }
            this.mainStorage[getHashModule(key)] = new Entry<>(key, value);
            this.size++;
            result = true;
            this.modCount++;
        }
        return result;
    }

    /**
     * @param key ключ.
     * @return Значения ключа, если такой ключ содержится в коллекции.
     * @throws NoSuchElementException если ключа нет в коллекции.
     */
    public V get(K key) {
        int keyHashModule = getHashModule(key);
        if (!this.mainStorage[keyHashModule].getKey().equals(key)) {
            throw new NoSuchElementException();
        }
        return this.mainStorage[keyHashModule].getValue();
    }

    /**
     * Класс, описывающий объекты, загружаемые в главное хранилище.
     * @param <K> Параметризованный тип ключа.
     * @param <V> Параметризованный тип значения.
     */
    static class Entry<K, V> {

        private K key;
        private V value;

        /**
         * Конструктор, инициализирует поля ключ, значение.
         * @param key ключ.
         * @param value значение.
         */
        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * @return ключ.
         */
        public K getKey() {
            return key;
        }

        /**
         * @return значение.
         */
        public V getValue() {
            return value;
        }
    }

    /**
     * Удаляет пару ключ - значение из хранилища.
     * @param key ключ заданной для удаления пары.
     * @return true - если пара удалена из коллекции.
     *         false - если ключ не найден в коллекции,
     * либо если объект ключ не совпадает с объектом хранящимся под вычисленным индексом(Возможно при коллизии).
     */
    public boolean delete(K key) {
        boolean result = false;
        int elementHashModule = getHashModule(key);
        if (this.mainStorage[elementHashModule] != null
                && this.mainStorage[elementHashModule].getKey().equals(key)) {
            this.mainStorage[elementHashModule] = null;
            result = true;
            this.size--;
            this.modCount++;
        }
        return result;
    }

    /**
     * Вычисляет хэш модуль ключа, в зависимости от размеров хэш таблицы.
     * @param key заданный для вычисления хэш модуля ключ.
     * @return хэш модуль заданного ключа.
     */
    private int getHashModule(K key) {
        return Math.abs(key.hashCode() % this.mainStorage.length);
    }

    /**
     * Вычисляет хэш модуль ключа.
     * @param key заданный для вычисления хэш модуля ключ.
     * @param newTableSize размер хэш таблицы, для вычисления хэш модуля.
     * @return хэш модуль заданного элемента.
     */
    private int getHashModule(K key, int newTableSize) {
        return key.hashCode() % newTableSize;
    }

    /**
     * Увеличивает размер таблицы и производит перехеширование
     * всех элементов, в соответствии с новым размером.
     */
    private void increaseCapacity() {
        Entry<K, V>[] newStorage = (Entry<K, V>[]) new Entry[this.mainStorage.length * MAGNIFICATION_FACTOR];
        for (Entry<K, V> entry : this.mainStorage) {
            if (entry != null) {
                newStorage[getHashModule(entry.getKey(), newStorage.length)] = entry;
            }
        }
        this.mainStorage = newStorage;
    }

    /**
     * @return возвращает итератор для обхода коллекции.
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {

            /**
             * Число изменений коллекции, на момент создания итератора.
             */
            private final int expectedModCount = modCount;

            /**
             * Указатель для обхода коллекции.
             */
            private int position;

            /**
             * @return возвращает индекс следующего ненулевого элемента.
             */
            private int findNextNotNullElement() {
                int result = -1;
                for (int i = this.position; i < mainStorage.length; i++) {
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
                return (findNextNotNullElement() != -1);
            }

            /**
             * Возвращает следующий не нулевой элемент за указателем.
             * Переводит указатель на текущий ненулевой элемент + 1.
             * @return ненулевой элемент.
             */
            @Override
            public Entry<K, V> next() {
                if (this.expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int nextNotNullElementIndex = findNextNotNullElement();
                Entry<K, V> result = mainStorage[findNextNotNullElement()];
                this.position = nextNotNullElementIndex + 1;
                return result;
            }
        };
    }
}
