package ru.job4j.array;

/**
 * Turn
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Turn {
    /**
     * back
     * @param array заданный массив.
     * @return перевернутый заданный массив.
     */
    public int[] back(int[] array) {
        for (int arrayIndex = 0; arrayIndex < array.length / 2; arrayIndex++) {
            int oldArray = array[arrayIndex];
            array[arrayIndex] = array[array.length - arrayIndex - 1];
            array[array.length - arrayIndex - 1] = oldArray;
        }
        return array;
    }
}
