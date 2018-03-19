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
public class SortUserTest {
    /**
     * Test sort.
     */
    @Test
    public void ifSortUserListThenReturnTreeMapWithSortingUsersById() {
        User first = new User("Egor", 1);
        User second = new User("Oleg", 2);
        User third = new User("Ivan", 3);
        SortUser testSort = new SortUser();
        Set<User> result = testSort.sort(new ArrayList<>(asList(first, third, second)));
        Set<User> expect = new LinkedHashSet<>(asList(first, second, third));
        assertThat(result, is(expect));
    }
    /**
     * Test sortNameLength.
     */
    @Test
    public void ifSortUsersListByNameLengthThenReturnSortedList() {
        User first = new User("Egor", 10);
        User second = new User("Roman", 25);
        User third = new User("Vasiliy", 20);
        SortUser testSort = new SortUser();
        List<User> result = testSort.sortNameLength(new ArrayList<>(asList(first, third, second)));
        List<User> expect = new ArrayList<>(asList(first, second, third));
        assertThat(result, is(expect));
    }
    /**
     * Test sortByAllFields.
     */
    @Test
    public void ifSortUserListByAllFieldsThenReturnSortedList() {
        User first = new User("Egor", 10);
        User second = new User("Roman", 25);
        User third = new User("Egor", 20);
        SortUser testSort = new SortUser();
        List<User> result = testSort.sortByAllFields(new ArrayList<>(asList(first, second, third)));
        List<User> expect = new ArrayList<>(asList(first, third, second));
        assertThat(result, is(expect));

    }
}
