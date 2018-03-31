package ru.job4j.generic;

import org.junit.Test;

import org.junit.Before;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test Store.
 * @author Denis Seleznev
 * @version $Id$
 * @since 31.03.2018
 */
public class StoreTest {

    Store<User> testUserStore;
    Store<Role> testRoleStore;
    User ivan10 = new User("10", "Ivan");
    User denis27 = new User("27", "Denis");
    Role junior10 = new Role("10", "Junior");
    Role mentor50 = new Role("50", "Mentor");

    @Before
    public void setUp() {
        testUserStore = new UserStore();
        testRoleStore = new RoleStore();
    }

    @Test
    public void ifAddObjectInStoreThenFindByIdReturnTrueObject() {
        testUserStore.add(ivan10);
        testRoleStore.add(mentor50);

        assertThat(testUserStore.findById("10").getName(), is("Ivan"));
        assertThat(testRoleStore.findById("50"), is(mentor50));
    }

    @Test(expected = NoSuchElementException.class)
    public void ifReplaceObjectThenFindByIdThrowNoSuchElementException() {
        testUserStore.add(denis27);
        testUserStore.replace("27", ivan10);

        testUserStore.findById("27");
    }

    @Test
    public void ifReplaceObjectThenFindByIdReturnNewObject() {
        testUserStore.add(denis27);
        testRoleStore.add(junior10);
        boolean result1 = testUserStore.replace("27", ivan10);
        boolean result2 = testRoleStore.replace("10", mentor50);

        assertThat(testUserStore.findById("10"), is(ivan10));
        assertThat(testRoleStore.findById("50"), is(mentor50));
        assertThat(result1, is(true));
        assertThat(result2, is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void ifDeleteObjectThenThrowNoSuchElementException() {
        testUserStore.add(denis27);
        testUserStore.delete("27");

        testUserStore.findById("27");
    }

    @Test
    public void ifFindByIdThenReturnTrueObject() {
        testUserStore.add(denis27);
        testRoleStore.add(junior10);

        assertThat(testUserStore.findById("27"), is(denis27));
        assertThat(testRoleStore.findById("10"), is(junior10));
    }
}