package ru.job4j.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для простых чисел.
 * @author Denis Seleznev
 * @version $Id$
 * @since 29.03.2018
 */
public class PrimeIterator implements Iterator {

    private final int[] array;
    private int position;
    private final boolean[] primes;

    /**
     * Конструктор, инициализирует поле array, и ставит указатель
     * position на индекс первого простого числа в массиве array.
     * В случае отсутствия простых чисел в массиве, указатель ставится на -1.
     * @param array заданный для итерации массив.
     */
    public PrimeIterator(int[] array) {
        this.array = array;
        this.primes = findPrimes(array);
        this.position = -1;
        this.position = findNextPrimeNumber();
    }

    /**
     * Находит следующее от указателя position простое число.
     * @return индекс следующего простого числа в массиве this.array, либо -1, если число не найдено.
     */
    private int findNextPrimeNumber() {
        int nextPrimeNumber = -1;
        for (int i = position + 1; i < array.length; i++) {
            if (primes[array[i]]) {
                nextPrimeNumber = i;
                break;
            }
        }
        return nextPrimeNumber;
    }

    /**
     * Находит все простые числа от 0 до максимального числа
     * в массиве array, используя алгоритм: решето Эратосфена.
     * @param array заданный, для итерации массив.
     * @return массив типа boolean, в котором индексу ячейки соответстует число,
     * а значение ячейки - true, если число простое и - false, если число составное.
     */
    private boolean[] findPrimes(int[] array) {
        int maxNumberOfArray = array[array.length - 1];
        boolean[] primes = new boolean[maxNumberOfArray + 1];
        Arrays.fill(primes, true);
        primes[0] = false;
        primes[1] = false;
        for (int i = 2; i < primes.length; ++i) {
            if (primes[i]) {
                for (int j = 2; i * j < primes.length; ++j) {
                    primes[i * j] = false;
                }
            }
        }
        return primes;
    }

    /**
     * Проверяет, есть ли в массиве после указателя простое число.
     * @return true - если простое число есть.
     *         false - если простых чисел больше нет.
     */
    @Override
    public boolean hasNext() {
        return this.position != -1;
    }

    /**
     * Возвращает текущий элемент и переводит указатель на следующее простое число.
     * @return простое число под индексом position в массиве array.
     * @throws NoSuchElementException в случае если текущий элемент, не является простым числом.
     */
    @Override
    public Object next() {
        if (this.position == -1) {
            throw new NoSuchElementException();
        }
        int currentPrimeNumber = array[position];
        position = findNextPrimeNumber();
        return currentPrimeNumber;
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
