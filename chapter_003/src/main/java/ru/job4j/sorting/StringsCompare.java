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
        List<Character> leftArr = new ArrayList<>();
        for (Character element : left.toCharArray()) {
            leftArr.add(element);
        }
        List<Character> rightArr = new ArrayList<>();
        for (Character element : right.toCharArray()) {
            rightArr.add(element);
        }
        List<Character> lowArray = leftArr.size() < rightArr.size() ? leftArr : rightArr;
        for (int i = 0; i < lowArray.size(); i++) {
            if (leftArr.get(i) != rightArr.get(i)) {
                return leftArr.get(i) - rightArr.get(i);
            }
        }
        return leftArr.size() - rightArr.size();
    }
}
