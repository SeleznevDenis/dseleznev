package ru.job4j.sorting;

import org.junit.Test;
import java.util.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class DepartmentsSortTest {

    /**
     * Массив строк, который будет отправлен на сортировку.
     */
    public String[] arrayForSorting = new String[]{
            "K1\\SK1",
            "K1\\SK2",
            "K1\\SK1\\SSK1",
            "K1\\SK1\\SSK2",
            "K2",
            "K2\\SK1\\SSK1",
            "K2\\SK1\\SSK2"
    };

    /**
     * Ожидаемый, отсортированный в лексикографическом порядке, массив строк.
     */
    public String[] naturalOrderSortingExpect = new String[]{
            "K1",
            "K1\\SK1",
            "K1\\SK1\\SSK1",
            "K1\\SK1\\SSK2",
            "K1\\SK2",
            "K2",
            "K2\\SK1",
            "K2\\SK1\\SSK1",
            "K2\\SK1\\SSK2"
    };

    /**
     * Ожидаемый, отсортированный в обратном лексикографическом порядке, массив строк.
     */
    public String[] reversOrderSortingExpect = new String[]{
            "K2",
            "K2\\SK1",
            "K2\\SK1\\SSK2",
            "K2\\SK1\\SSK1",
            "K1",
            "K1\\SK2",
            "K1\\SK1",
            "K1\\SK1\\SSK2",
            "K1\\SK1\\SSK1"
    };

    /**
     * Test naturalOrderSort.
     */
    @Test
    public void ifArrayNaturalOrderSortingThenResultIsNaturalOrderSortingExpect() {
        DepartmentsSort testSort = new DepartmentsSort();
        Set<String> result = testSort.naturalOrderSort(arrayForSorting);

        assertThat(result, is(new LinkedHashSet<>(asList(naturalOrderSortingExpect))));
    }

    /**
     * Test reverseOrderSort.
     */
    @Test
    public void ifArrayReversOrderSortingThenResultIsReversOrderSortingExpect() {
        DepartmentsSort testSort = new DepartmentsSort();
        Set<String> result = testSort.reverseOrderSort(arrayForSorting);
        assertThat(result, is(new LinkedHashSet<>(asList(reversOrderSortingExpect))));
    }
}
