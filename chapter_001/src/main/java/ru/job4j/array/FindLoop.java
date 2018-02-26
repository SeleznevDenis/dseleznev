package ru.job4j.array;

/**
 * FindLoop
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */

public class FindLoop {
    /**
     * indexOf
     * @param data заданный массив
     * @param el заданное для поиска число.
     * @return индекс заданного для поиска числа, либо -1 если числа нет в массиве.
     */
    public int indexOf(int[] data, int el) {
        int index = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == el) {
                index = i;
                break;
            }
        }
        return index;
    }
}
