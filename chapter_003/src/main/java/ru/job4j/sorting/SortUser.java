package ru.job4j.sorting;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class SortUser {
    /**
     * Упорядочивает пользователей User по id в порядке возрастания.
     * Метод compareTo, которым пользуется TreeSet, реализован в классе User.
     * @param list заданный для сортировки.
     * @return TreeSet отсортированный по id.
     */
    public Set<User> sort (List<User> list) {
        return new TreeSet<>(list);
    }
}
