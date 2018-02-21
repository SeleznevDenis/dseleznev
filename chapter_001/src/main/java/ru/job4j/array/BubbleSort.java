package ru.job4j.array;

/**
 * Buble sort
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */

public class BubbleSort {
    /**
     * sort сортировка заданного массива, используя алгоритм сортировки пузырьком.
     * @param array заданный массив.
     * @return отсортированный от меньшего числа к большему массив.
     */
    public int[] sort(int[] array) {
        for (int arrayLength = array.length; arrayLength != 0; arrayLength--) {
            for (int i = 0; i < arrayLength - 1; i++) {
                if (array[i] > array[i + 1]) {
                    int tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                }
            }
        }
        return array;
    }
}
