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
        for (int axisI = 0; axisI < size; axisI++) {
            for (int axisJ = 0; axisJ < size; axisJ++) {
                table[axisI][axisJ] = (axisI + 1) * (axisJ + 1);
            }
        }
        return table;
    }
}
