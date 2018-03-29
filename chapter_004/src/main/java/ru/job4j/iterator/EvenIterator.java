package ru.job4j.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор массива четных чисел.
 * @author Denis Seleznev
 * @version $Id$
 * @since 29.03.2018
 */
public class EvenIterator implements Iterator {

    private final int[] array;
    private int position;

    /**
     * Конструктор, инициализирует массив array и ставит указатель position
     * на первое чётное число если оно есть, в противном случае указатель остается на -1.
     * @param array массив чисел.
     */
    public EvenIterator(int[] array) {
        this.array = array;
        position = -1;
        position = findNextEvenElement();
    }

    /**
     * Ищет следующее четное число после указателя в массиве array,
     * Если число не найдено, возвращает -1.
     * @return индекс четного числа в массиве array, либо -1.
     */
    private int findNextEvenElement() {
        int result = -1;
        for (int i = this.position + 1; i < this.array.length; i++) {
            if (array[i] % 2 == 0) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Проверяет наличие четных чисел после указателя.
     * @return true, если далее четные числа есть,
     *         false, если далее четных чисел  нет.
     */
    @Override
    public boolean hasNext() {
        return (this.position != -1);
    }

    /**
     * Возвращает текущий элемент и переводит указатель на следующее четное число.
     * @return четное число под индексом position в массиве array.
     * @throws NoSuchElementException в случае если четное число отсутствует.
     */
    @Override
    public Object next() {
        if (this.position == -1) {
            throw new NoSuchElementException();
        }
        int result = array[this.position];
        this.position = findNextEvenElement();
        return result;
    }

    /**
     * Метод не поддерживается в данной реализации и
     * выбрасывает исключение при попытке использования.
     * @throws UnsupportedOperationException Операция не поддерживается.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
