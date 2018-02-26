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
     * @param sorted заданный массив.
     * @return отсортированный от меньшего числа к большему массив.
     */
    public int[] sort(int[] sorted) {
        for (int sortArea = sorted.length; sortArea != 0; sortArea--) {
            for (int i = 0; i < sortArea - 1; i++) {
                if (sorted[i] > sorted[i + 1]) {
                    int oldArray = sorted[i];
                    sorted[i] = sorted[i + 1];
                    sorted[i + 1] = oldArray;
                }
            }
        }
        return sorted;
    }
}
