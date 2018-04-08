package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для двумерного массива.
 * @author Denis Seleznev
 * @version $Id$
 * @since 28.03.2018
 */
public class MatrixIterator implements Iterator {

    private final int[][] array;
    private int row;
    private int column;

    /**
     * Конструктор, инициализирует поле array.
     * @param array двумерный массив.
     */
    public MatrixIterator(int[][] array) {
        this.array = array;
    }

    /**
     * Проверяет наличие следующего элемента.
     * @return true - следующий элемент есть,
     *         false - массив закончился.
     */
    @Override
    public boolean hasNext() {
        return (row < array.length && column < array[row].length);
    }

    /**
     * Возвращает текущий элемент и сдвигает указатели.
     * @return текущий элемент массива.
     * @throws NoSuchElementException - в случае пустого массива.
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int result = array[row][column++];
        if (column >= array[row].length) {
            column = 0;
            row++;
        }
        return result;
    }
}
