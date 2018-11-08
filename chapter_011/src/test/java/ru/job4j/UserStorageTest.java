package ru.job4j;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

/**
 * User Storage Test.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserStorageTest {

    private ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

    @Test
    public void add() {
        Storage memory = new MemoryStorage();
        UserStorage storage = new UserStorage(memory);
        storage.add(new User());
    }

    @Test
    public void whenLoadContextShouldGetUserStorageBean() {
        UserStorage memory = this.context.getBean("storage", UserStorage.class);
        memory.add(new User());
        assertNotNull(memory);
    }

   @Test
    public void whenLoadContextShouldGetJdbcStorageBean() {
        UserStorage memory = this.context.getBean("jdbcStorage", UserStorage.class);
        memory.add(new User());
        assertNotNull(memory);
    }

    @Test
    public void whenAddToMemoryThenMemoryShouldReturnAddedItemById() {
        UserStorage memory = this.context.getBean("storage", UserStorage.class);
        User usr = new User();
        usr.setName("test");
        int id = memory.add(usr);
        assertThat(memory.findById(id).getName(), is(usr.getName()));
    }
}