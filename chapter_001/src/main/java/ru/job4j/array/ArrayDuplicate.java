package ru.job4j.array;

import java.util.Arrays;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicate {
    /**
     * remove
     * @param array заданный массив.
     * @return заданный массив без слов повторов.
     */
    public String[] remove(String[] array) {
        int repeatedWords = 0;
        for (int wordIndex = 0; wordIndex < array.length - 1; wordIndex++) {
            for (int comparedWordIndex = 0; comparedWordIndex < array.length - 1 - repeatedWords; comparedWordIndex++) {
                if (comparedWordIndex == wordIndex) {
                    comparedWordIndex++;
                 } else if (array[wordIndex].equals(array[comparedWordIndex])) {
                    String tmp = array[comparedWordIndex];
                    array[comparedWordIndex] = array[array.length - repeatedWords - 1];
                    array[array.length - repeatedWords - 1] = tmp;
                    repeatedWords++;
                }
            }
        }
        return Arrays.copyOf(array, array.length - repeatedWords);
    }
}
