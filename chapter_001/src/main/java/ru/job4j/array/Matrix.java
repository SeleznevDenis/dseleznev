package ru.job4j.array;

/**
 * Matrix
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */

public class Matrix {
    /**
     * multiple
     * @param size размер таблицы.
     * @return таблица умножения соответственно размеру.
     */
    int[][] multiple(int size) {
        int[][] table = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = (i + 1) * (j + 1);
            }
        }
        return table;
    }
}
