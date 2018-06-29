package ru.job4j.servlets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Test ValidateService.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 0.1
 */
public class ValidateServiceTest {

    private ValidateService testValidator = ValidateService.getInstance();
    private User firstTestUser;
    private User secondTestUser;

    @Before
    public void setUp() {
        this.firstTestUser = new User("first", "test", "test");
        this.secondTestUser = new User("second", "test", "test");
    }
    @After
    public void clearTestStore() {
        this.testValidator.delete(this.firstTestUser.getId());
        this.testValidator.delete(this.secondTestUser.getId());
    }

    @Test
    public void getInstanceForAllTimeShouldBeReturnSomeObject() {
        ValidateService secondTestValidator = ValidateService.getInstance();
        assertThat(this.testValidator, is(secondTestValidator));
    }

    @Test
    public void ifAddUserInToStoreThenStoreShouldHaveUser() {
        assertThat(this.testValidator.add(this.firstTestUser), is(true));
        assertThat(this.testValidator.add(new User()), is(false));
        assertThat(this.testValidator.findById(this.firstTestUser.getId()), is(this.firstTestUser));
    }

    @Test
    public void ifUpdateUserThenUserFieldsMustBeUpdated() {
        this.testValidator.add(this.firstTestUser);
        this.secondTestUser.setId(this.firstTestUser.getId());
        User thirdUser = new User();
        int nonexistentId = 0;
        for (User user : this.testValidator.findAll()) {
            if (nonexistentId < user.getId()) {
                nonexistentId = user.getId();
            }
        }
        nonexistentId++;
        thirdUser.setId(nonexistentId);
        assertThat(this.testValidator.update(this.secondTestUser), is(true));
        assertThat(this.testValidator.findById(this.firstTestUser.getId()), is(this.secondTestUser));
        assertThat(this.testValidator.update(thirdUser), is(false));
    }

    @Test
    public void ifDeleteUserThenStoreShouldNotContainUser() {
        this.testValidator.add(this.firstTestUser);
        assertThat(this.testValidator.delete(this.firstTestUser.getId()), is(true));
        assertNull(this.testValidator.findById(this.firstTestUser.getId()));
        assertThat(this.testValidator.delete(this.firstTestUser.getId()), is(false));
    }

    @Test
    public void findAllMustReturnAllUsersFromStore() {
        this.testValidator.add(firstTestUser);
        this.testValidator.add(secondTestUser);
        assertThat(
                this.testValidator.findAll(), is(
                        new ArrayList<>(asList(this.firstTestUser, this.secondTestUser))
                )
        );
    }
}