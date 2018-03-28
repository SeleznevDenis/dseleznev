package ru.job4j.sorting;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class DepartmentsSort {

    /**
     * Сортирует множество подразделений в лексикографическом порядке,
     * а также - добавляет верхнеуровневые подразделения, в случае, если
     * подразделение есть, а верхнеуровневое пропущено.
     * @param departments массив подразделений.
     * @return отсортированное в лексикографическом порядке, множество подразделений.
     */
    public Set<String> naturalOrderSort(String[] departments) {
        TreeSet<String> sortedDepartments = new TreeSet<>();
        for (String fullString : departments) {
            StringBuilder department = new StringBuilder();
            for (String splitString : fullString.split("\\\\")) {
                department.append(splitString);
                sortedDepartments.add(department.toString());
                department.append("\\");
            }
        }
        return sortedDepartments;
    }

    /**
     * Сортирует множество подразделений в обратном лексикографическом порядке,
     * а также - добавляет верхнеуровневые подразделения, в случае, если
     * подразделение есть, а верхнеуровневое пропущено.
     * @param departments массив подразделений.
     * @return отсортированное в обратном лексикографическом порядке, множество подразделений.
     */
    public TreeSet<String> reverseOrderSort(String[] departments) {
        TreeSet<String> sortedDepartments = new TreeSet<>(Comparator.reverseOrder());
        sortedDepartments.addAll(naturalOrderSort(departments));
        return sortedDepartments;
    }
}
