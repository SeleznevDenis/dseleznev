package ru.job4j.monitore;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Test UserStore.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 28.04.2016
 */
public class UserStoreTest {

    private UserStore testStore;

    @Before
    public void setUp() {
        this.testStore = new UserStore();
    }

    @Test
    public void ifAdd4UsersFrom2ThreadsStoreShouldHave4Users() {
        Thread first = new Thread(() -> {
            testStore.add(new User(1, 10));
            testStore.add(new User(2, 20));
        });
        Thread second = new Thread(() -> {
            testStore.add(new User(3, 30));
            testStore.add(new User(4, 40));
        });
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(testStore.findUserById(1).getAmount(), is(10));
        assertThat(testStore.findUserById(2).getAmount(), is(20));
        assertThat(testStore.findUserById(3).getAmount(), is(30));
        assertThat(testStore.findUserById(4).getAmount(), is(40));
    }

    @Test
    public void ifUpdateUserThenStoreCanReturnUserWithNewAmount() {
        Boolean addResult = testStore.add(new User(1, 10));
        Boolean updateResult = testStore.update(new User(1, 20));
        assertThat(addResult, is(true));
        assertThat(updateResult, is(true));
        assertThat(testStore.findUserById(1).getAmount(), is(20));
    }

    @Test
    public void ifDeleteUserThenStoreHasNotUserWithSameId() {
        testStore.add(new User(1, 10));
        Boolean deleteResult = testStore.delete(new User(1, 10));
        assertThat(deleteResult, is(true));
        assertNull(testStore.findUserById(1));
    }

    @Test
    public void ifTransferThenAmountWasChanged() {
        testStore.add(new User(1, 50));
        testStore.add(new User(2, 50));
        Thread first = new Thread(() -> {
            testStore.transfer(1, 2, 5);
            testStore.transfer(1, 2, 15);
        });
        Thread second = new Thread(() -> {
            testStore.transfer(2, 1, 10);
            testStore.transfer(2, 1, 20);
        });
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(testStore.findUserById(1).getAmount(), is(60));
        assertThat(testStore.findUserById(2).getAmount(), is(40));
    }
}