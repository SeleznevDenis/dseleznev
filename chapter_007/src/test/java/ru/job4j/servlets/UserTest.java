package ru.job4j.servlets;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test User.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 0.1
 */
public class UserTest {

    @Test
    public void ifUsersAreSameThenEqualsShouldBeReturnTrue() {
        User first = new User();
        first.setId(1);
        User second = new User();
        second.setId(1);
        User third = new User();
        third.setId(2);
        assertThat(first.equals(second), is(true));
        assertThat(second.equals(first), is(true));
        assertThat(first.hashCode(), is(second.hashCode()));
        assertThat(first.equals(third), is(false));
        assertThat(third.equals(first), is(false));
    }
}