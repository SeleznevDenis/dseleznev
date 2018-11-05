package ru.job4j;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * User Storage Test.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserStorageTest {

    @Test
    public void add() {
        Storage memory = new MemoryStorage();
        UserStorage storage = new UserStorage(memory);
        storage.add(new User());
    }

    @Test
    public void whenLoadContextShouldGetBeans() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        UserStorage memory = context.getBean(UserStorage.class);
        memory.add(new User());
        assertNotNull(memory);
    }
}