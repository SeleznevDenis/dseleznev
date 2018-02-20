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
        int rsl = -1;

        for (int index = 0; index < data.length; index++) {
            if (data[index] == el) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }
}
