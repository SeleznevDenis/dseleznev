package ru.job4j.array;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArrayAddition {

    /**
     * addition - складывает 2 заведомо отсортированных массива целых чисел.
     * @param a1 первый заданный массив.
     * @param a2 второй заданный массив.
     * @return отсортированный массив сложенный из первого и второго заданных.
     */
    public int[] addition(int[] a1, int[] a2) {

        int[] result = new int[a1.length + a2.length];
        int i = 0;
        int j = 0;

        for (int k = 0; k < result.length; k++) {
            if (i < a1.length && j < a2.length) {
                if (a1[i] <= a2[j]) {
                    result[k] = a1[i];
                    i++;
                } else {
                    result[k] = a2[j];
                    j++;
                }
            } else if (i < a1.length) {
                result[k] = a1[i];
                i++;
            } else {
                result[k] = a2[j];
                j++;
            }
        }
        return result;
    }
}
