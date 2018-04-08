package ru.job4j.map;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class UserTest {

    @Test
    public void testEqualsAndHashCode() {
        Map<User, Object> testMap = new HashMap<>();
        Calendar birthday = new GregorianCalendar(1990, 9, 22);
        Object obj = new Object();
        User first = new User("Ivan", 2, birthday);
        User second = new User("Ivan", 2, birthday);
        testMap.put(first, obj);
        testMap.put(second, obj);
        System.out.println(testMap);
    }
}