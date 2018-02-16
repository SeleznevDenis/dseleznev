package ru.job4j.max;

/**
 * Max
 * Объект класса Max содержит метод, который возвращает большее число из двух чисел.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Max {
    /**
     * max
     * @param first первое сравниваемое число.
     * @param second второе сравниваемое число.
     * @return наибольшее число из двух сравниваемых чисел.
     */
    public int max(int first, int second) {
        return first >= second ? first : second;
    }
}
