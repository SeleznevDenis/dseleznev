package ru.job4j.tracker;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import java.util.ArrayList;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;

/**
 * Test Tracker.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class TrackerTest {

    /**
     * Test add.
     */
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    /**
     * Test replace.
     */
    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = new Item("test1", "testDescription", 123L);
        tracker.add(previous);
        Item next = new Item("test2", "testDescription2", 1234L);
        next.setId(previous.getId());
        tracker.replace(previous.getId(), next);
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    /**
     * Test delete.
     */
    @Test
    public void whenDeleteCheckedItemThenThisItemNull() {
        Tracker tracker = new Tracker();
        Item checked = new Item("test3", "testDescription3", 12345L);
        tracker.add(checked);
        tracker.delete(checked.getId());
        assertNull(tracker.findById(checked.getId()));
    }

    /**
     * Test findAll.
     */
    @Test
    public void whenItemsHasTwoNotNullItemThenFindAllReturnArrayWithTwoItems() {
        Tracker tracker = new Tracker();
        Item first = new Item("test4", "testDescription4", 5454L);
        Item second = new Item("test5", "testDescription5", 54L);
        tracker.add(first);
        tracker.add(second);
        ArrayList<Item> itemsResult = tracker.findAll();
        String[] except = {"test4", "test5"};
        String[] result = {itemsResult.get(0).getName(), itemsResult.get(itemsResult.size() - 1).getName()};
        assertThat(result, is(except));
    }

    /**
     * Test findByName.
     */
    @Test
    public void ifFindByNameTest6ThenReturnItemWithNameTest6() {
        Tracker tracker = new Tracker();
        Item testItem = new Item("Test6", "textDescription", 87L);
        tracker.add(testItem);
        assertThat(tracker.findByName("Test6").get(0), is(testItem));
    }

    /**
     * Test findById.
     */
    @Test
    public void ifFindByIdItemThenReturnItemWithThisId() {
        Tracker tracker = new Tracker();
        Item testItem = new Item("test", "testDesc", 8L);
        tracker.add(testItem);
        assertThat(tracker.findById(testItem.getId()), is(testItem));
    }
}
