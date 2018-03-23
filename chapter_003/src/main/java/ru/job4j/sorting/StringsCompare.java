package ru.job4j.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StringsCompare implements Comparator<String> {
    /**
     * Сравнивает левую и правую строки в лексикографическом порядке.
     * @param left левая строка.
     * @param right правая строка.
     * @return результат сравнения.
     */
    @Override
    public int compare(String left, String right) {
        for (int i = 0; i < Math.min(left.length(), right.length()); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                return left.charAt(i) - right.charAt(i);
            }
        }
        return left.length() - right.length();
    }
}
