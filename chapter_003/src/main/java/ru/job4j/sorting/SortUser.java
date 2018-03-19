package ru.job4j.sorting;

import java.util.Comparator;
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
    public Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }

    /**
     * Упорядочивает пользователей User по длинне имени в порядке увеличения длины.
     * @param list заданный для сортировки.
     * @return отсортированный List.
     */
    public List<User> sortNameLength(List<User> list) {
        list.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getName().length() - o2.getName().length();
                    }
                }
        );
        return list;
    }

    /**
     * Упорядочивает пользователей User по имени в лексикографическом
     * порядке и по возрасту в порядке возрастания, если имена одинаковые.
     * @param list заданный для сортировки.
     * @return отсортированный List.
     */
    public List<User> sortByAllFields(List<User> list) {
        list.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getName().equals(o2.getName())
                                ? o1.getAge() - o2.getAge() : o1.getName().compareTo(o2.getName());
                    }
                }
        );
        return list;
    }
}
