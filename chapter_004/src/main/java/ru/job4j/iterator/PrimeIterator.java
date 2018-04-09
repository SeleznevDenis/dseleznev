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
    private boolean[] primes;

    /**
     * Конструктор, инициализирует поле array, и ставит указатель
     * position на индекс первого простого числа в массиве array.
     * В случае отсутствия простых чисел в массиве, указатель ставится на -1.
     * @param array заданный для итерации массив.
     */
    public PrimeIterator(int[] array) {
        this.array = array;
    }

    /**
     * Находит следующее от указателя position простое число.
     * @return индекс следующего простого числа в массиве this.array, либо -1, если число не найдено.
     */
    private int findNextPrimeNumber() {
        int nextPrimeNumber = -1;
        for (int i = this.position; i < array.length; i++) {
            if (findPrimes()[this.array[i]]) {
                nextPrimeNumber = i;
                break;
            }
        }
        return nextPrimeNumber;
    }

    /**
     * При первом вызове, находит все простые числа от 0 до максимального числа
     * в массиве array, используя алгоритм: решето Эратосфена. И сохраняет их в this.primes.
     * @return this.primes, в котором индексу ячейки соответстует число,
     * а значению ячейки - true, если число простое и - false, если число составное.
     */
    private boolean[] findPrimes() {
        if (this.primes == null) {
            int maxNumberOfArray = this.array[this.array.length - 1];
            this.primes = new boolean[maxNumberOfArray + 1];
            Arrays.fill(this.primes, true);
            this.primes[0] = false;
            this.primes[1] = false;
            for (int i = 2; i < primes.length; ++i) {
                if (this.primes[i]) {
                    for (int j = 2; i * j < this.primes.length; ++j) {
                        this.primes[i * j] = false;
                    }
                }
            }
        }
        return this.primes;
    }

    /**
     * Проверяет, есть ли в массиве после указателя простое число.
     * @return true - если простое число есть.
     *         false - если простых чисел больше нет.
     */
    @Override
    public boolean hasNext() {
        return findNextPrimeNumber() != -1;
    }

    /**
     * Возвращает простое число за указателем и переводит указатель на индекс найденного простого числа + 1.
     * @return простое число.
     * @throws NoSuchElementException в случае если в массиве нет простых чисел за указателем.
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int nextPrimeNumber = findNextPrimeNumber();
        int result = array[nextPrimeNumber];
        this.position = nextPrimeNumber + 1;
        return result;
    }

    /**
     * Метод не поддерживается в данной реализации и
     * генерирует исключение при попытке использования.
     * @throws UnsupportedOperationException Операция не поддерживается.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
