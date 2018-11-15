package ru.job4j.persistens.dao;

import org.junit.Test;
import ru.job4j.persistens.models.User;
import ru.job4j.utils.SingletonSF;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserDaoTest extends CrudDAOTest<User> {
    @Override
    protected UserDao getTestDao() {
        return new UserDao(SingletonSF.getSessionFactory());
    }

    @Override
    protected User getTestEntity() {
        User result = new User();
        result.setDescription("test");
        result.setLogin("testLogin");
        result.setPassword("testPassword");
        return result;
    }

    @Override
    protected User getSecondTestEntity() {
        User result = new User();
        result.setDescription("test2");
        result.setLogin("testLogin2");
        return result;
    }

    @Test
    public void findUserByLogin() {
        User expect = new User();
        expect.setLogin("testLogin10");
        expect.setPassword("testPassword10");
        this.getTestDao().add(expect);
        assertThat(this.getTestDao().findUserByLogin(expect.getLogin()).getPassword(), is(expect.getPassword()));
    }
}