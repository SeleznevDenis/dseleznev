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
}
