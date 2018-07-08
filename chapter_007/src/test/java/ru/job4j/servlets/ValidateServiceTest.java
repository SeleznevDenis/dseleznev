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
 * Test ValidateService.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 0.1
 */
public class ValidateServiceTest {

    private ValidateService testValidator = ValidateService.getInstance();

    @After
    public void cleanBase() {
        for (User user : this.testValidator.findAll()) {
            if (user.getId() != 0) {
                testValidator.delete(user.getId());
            }
        }
    }

    @Test
    public void getInstanceForAllTimeShouldBeReturnSomeObject() {
        ValidateService secondTestValidator = ValidateService.getInstance();
        assertThat(this.testValidator, is(secondTestValidator));
    }

    @Test
    public void ifAddUserInToStoreThenStoreShouldHaveUser() {
        User badUser = new User();
        badUser.setName("test");
        boolean addGoodUser = this.testValidator.add(new User("validateTest", "test", "test", "user", "test"));
        boolean containsUser = false;
        for (User user : this.testValidator.findAll()) {
            if (user.getName().equals("validateTest")) {
                containsUser = true;
            }
        }
        assertThat(this.testValidator.add(badUser), is(false));
        assertThat(addGoodUser, is(true));
        assertThat(containsUser, is(true));
    }

    @Test
    public void ifUpdateUserThenUserFieldsMustBeUpdated() {
        this.testValidator.add(new User("testValidator", "test", "test", "user", "test"));
        int userId = 0;
        for (User user : this.testValidator.findAll()) {
            if (user.getName().equals("testValidator")) {
                userId = user.getId();
                break;
            }
        }
        User up = new User("up", "up", "up", "user", "test");
        up.setId(userId);
        boolean upResult = this.testValidator.update(up);
        User dontUp = new User();
        dontUp.setId(userId + 1);
        assertThat(upResult, is(true));
        assertThat(this.testValidator.update(dontUp), is(false));
        assertThat(this.testValidator.findById(userId).getName(), is("up"));
    }

    @Test
    public void ifDeleteUserThenStoreShouldNotContainUser() {
        this.testValidator.add(new User("test", "test", "test", "user", "test"));
        int userId = 0;
        for (User user : this.testValidator.findAll()) {
            if (user.getName().equals("test")) {
                userId = user.getId();
                break;
            }
        }
        assertThat(this.testValidator.delete(userId), is(true));
        assertNull(this.testValidator.findById(userId));
        assertThat(this.testValidator.delete(userId), is(false));
    }
}