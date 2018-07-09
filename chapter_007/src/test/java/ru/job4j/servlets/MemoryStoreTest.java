package ru.job4j.servlets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Test MemoryStore.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class MemoryStoreTest {

    private MemoryStore testStore = MemoryStore.getInstance();
    private User firstTestUser;
    private User secondTestUser;

    @Before
    public void setUp() {
        this.firstTestUser = new User();
        this.secondTestUser = new User();
        this.firstTestUser.setName("first");
        this.firstTestUser.setLogin("first");
        this.secondTestUser.setLogin("second");
        this.secondTestUser.setName("second");
    }
    @After
    public void clearTestStore() {
        this.testStore.delete(this.firstTestUser.getId());
        this.testStore.delete(this.secondTestUser.getId());
    }

    @Test
    public void ifFindByLoginThenStorageShouldBeReturnUserWithGivenLogin() {
        this.testStore.add(this.firstTestUser);
        assertThat(this.testStore.findByLogin(this.firstTestUser.getLogin()), is(this.firstTestUser));
    }

    @Test
    public void ifGetTwoInstanceThenTheyShouldBeOneObject() {
        MemoryStore secondTestStore = MemoryStore.getInstance();
        assertThat(this.testStore, is(secondTestStore));
    }

    @Test
    public void ifAddUserThenStoreHasThisUser() {
        this.testStore.add(this.firstTestUser);
        assertThat(this.testStore.findById(this.firstTestUser.getId()), is(this.firstTestUser));
    }

    @Test
    public void ifUpdateUserNameThenUserShouldHasNewName() {
        this.testStore.add(this.firstTestUser);
        int idForUpdate = this.firstTestUser.getId();
        this.secondTestUser.setId(idForUpdate);
        this.testStore.update(this.secondTestUser);
        assertThat(this.testStore.findById(idForUpdate), is(this.secondTestUser));
    }

    @Test
    public void ifDeleteUserThenStorageHasNotUser() {
        this.testStore.add(this.firstTestUser);
        this.testStore.delete(this.firstTestUser.getId());
        assertNull(this.testStore.findById(this.firstTestUser.getId()));
    }

    @Test
    public void ifAddTwoUsersInToStorageThenStorageShouldContainThisUsers() {
        this.testStore.add(firstTestUser);
        this.testStore.add(secondTestUser);
        List<User> result = this.testStore.findAll();
        List<User> expect = new ArrayList<>(asList(this.firstTestUser, this.secondTestUser));
        assertThat(result, is(expect));
    }
}