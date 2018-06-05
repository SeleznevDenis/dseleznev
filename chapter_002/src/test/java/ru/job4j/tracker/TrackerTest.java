package ru.job4j.tracker;

import org.junit.After;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;

/**
 * Test Tracker.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class TrackerTest {

    @After
    public void cleanDB() throws IOException, SQLException {
        Properties properties = new Properties();
        try (InputStream reader = getClass().getClassLoader().getResourceAsStream("queries.properties")) {
            properties.load(reader);
        }
        try (Connection connect = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password")
        )) {
            connect.createStatement().execute("DROP TABLE tracker");
        }
    }

    /**
     * Test add.
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item item = new Item("test1", "testDescription", 123L);
            Item expect = tracker.add(item);
            assertThat(tracker.findAll().get(0), is(expect));
        }
    }

    /**
     * Test replace.
     */
    @Test
    public void whenReplaceNameThenReturnNewName() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item previous = new Item("test1", "testDescription", 123L);
            tracker.add(previous);
            Item next = new Item("test2", "testDescription2", 1234L);
            next.setId(previous.getId());
            tracker.replace(previous.getId(), next);
            assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
        }
    }

    /**
     * Test delete.
     */
    @Test
    public void whenDeleteCheckedItemThenThisItemNull() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item checked = new Item("test3", "testDescription3", 12345L);
            tracker.add(checked);
            tracker.delete(checked.getId());
            assertNull(tracker.findById(checked.getId()));
        }
    }

    /**
     * Test findAll.
     */
    @Test
    public void whenItemsHasTwoNotNullItemThenFindAllReturnArrayWithTwoItems() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item first = new Item("test4", "testDescription4", 5454L);
            Item second = new Item("test5", "testDescription5", 54L);
            tracker.add(first);
            tracker.add(second);
            ArrayList<Item> itemsResult = tracker.findAll();
            String[] except = {"test4", "test5"};
            String[] result = {itemsResult.get(0).getName(), itemsResult.get(itemsResult.size() - 1).getName()};
            assertThat(result, is(except));
        }
    }

    /**
     * Test findByName.
     */
    @Test
    public void ifFindByNameTest6ThenReturnItemWithNameTest6() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item testItem = new Item("Test6", "textDescription", 87L);
            tracker.add(testItem);
            assertThat(tracker.findByName("Test6").get(0), is(testItem));
        }
    }

    /**
     * Test findById.
     */
    @Test
    public void ifFindByIdItemThenReturnItemWithThisId() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item testItem = new Item("test", "testDesc", 8L);
            Item expect = tracker.add(testItem);
            assertThat(tracker.findById(expect.getId()), is(expect));
        }
    }
}
