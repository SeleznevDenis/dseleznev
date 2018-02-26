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
        int[] a3 = new int[a1.length + a2.length];
        int a1Index = 0;
        int a2Index = 0;
        for (int a3Index = 0; a3Index < a3.length; a3Index++) {
            if (a1Index < a1.length && a2Index < a2.length) {
                if (a1[a1Index] <= a2[a2Index]) {
                    a3[a3Index] = a1[a1Index];
                    a1Index++;
                } else {
                    a3[a3Index] = a2[a2Index];
                    a2Index++;
                }
            } else if (a1Index < a1.length) {
                a3[a3Index] = a1[a1Index];
                a1Index++;
            } else {
                a3[a3Index] = a2[a2Index];
                a2Index++;
            }
        }
        return a3;
    }
}
