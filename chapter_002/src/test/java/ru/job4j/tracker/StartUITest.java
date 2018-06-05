package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;

/**
 * Test StartUI.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StartUITest {

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final String ln = System.lineSeparator();
    private final String menu = new StringJoiner(ln)
                                  .add("Menu:")
                                  .add("0. Add new item")
                                  .add("1. Show all items")
                                  .add("2. Edit item")
                                  .add("3. Delete item")
                                  .add("4. Find item by id")
                                  .add("5. Find items by name")
                                  .add("6. Exit prigram")
                                  .toString();

    /**
     * Меняем дефолтный вывод в консоль на буфер ByteArrayOutputStream out.
     */
    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    /**
     * Возвращаем дефолтный вывод в консоль.
     */
    @After
    public void backOutput() throws IOException, SQLException {
        System.setOut(stdout);
        TrackerTest cleaner = new TrackerTest();
        cleaner.cleanDB();
    }

    /**
     * Test createItem.
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
            new StartUI(input, tracker).init();
            assertThat(tracker.findAll().get(0).getName(), is("test name"));
        }
    }

    /**
     * Test editItem.
     */
    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item item = tracker.add(new Item());
            Input input = new StubInput(new String[]{"2", item.getId(), "Y", "test name", "desc", "6"});
            new StartUI(input, tracker).init();
            assertThat(tracker.findById(item.getId()).getName(), is("test name"));
        }
    }

    /**
     * Test deleteItem.
     */
    @Test
    public void whenDeleteItemThenItemNull() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item checked = tracker.add(new Item());
            Input input = new StubInput(new String[]{"3", checked.getId(), "Y", "6"});
            new StartUI(input, tracker).init();
            assertNull(tracker.findById(checked.getId()));
        }
    }

    /**
     * Test showAllItems.
     */
    @Test
    public void whenShowAllItemsThenOutputAllItems() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item first = new Item("test1", "testDesc1", 10L);
            Item second = new Item("test2", "testDesc2", 11L);
            first.setId("1");
            second.setId("2");
            tracker.add(first);
            tracker.add(second);
            Input input = new StubInput(new String[]{"1", "6"});
            new StartUI(input, tracker).init();
            assertThat(
                    new String(out.toByteArray()),
                    is(
                            new StringJoiner(ln)
                                    .add(menu)
                                    .add("---------------- All Items ------------------")
                                    .add("1 | test1")
                                    .add("testDesc1")
                                    .add("2 | test2")
                                    .add("testDesc2")
                                    .add("------------------- End ---------------------")
                                    .add(menu + ln)
                                    .toString()
                    )
            );
        }
    }

    /**
     * Test findById.
     */
    @Test
    public void whenFindByIdTestItemThenOutputTestItem() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item checked = new Item("test", "testDesc", 10L);
            checked.setId("1");
            tracker.add(checked);
            Input input = new StubInput(new String[]{"4", checked.getId(), "n", "6"});
            new StartUI(input, tracker).init();
            assertThat(
                    new String(out.toByteArray()),
                    is(
                            new StringJoiner(ln)
                                    .add(menu)
                                    .add("---------------- Show item ------------------")
                                    .add("Selected item : ")
                                    .add("1 | test")
                                    .add("testDesc")
                                    .add("---------------- Show item end --------------")
                                    .add(menu + ln)
                                    .toString()
                    )
            );
        }
    }

    /**
     * Test findByName.
     */
    @Test
    public void whenFindByNameTestThenOutputTestItem() {
        try (Tracker tracker = new Tracker()) {
            tracker.init();
            Item checked = new Item("test", "testDesc", 10L);
            checked.setId("1");
            tracker.add(checked);
            Input input = new StubInput(new String[]{"5", "test", "6"});
            new StartUI(input, tracker).init();
            assertThat(
                    new String(out.toByteArray()),
                    is(
                            new StringJoiner(ln)
                                    .add(menu)
                                    .add("----------------Find items by name-----------")
                                    .add("1 | test")
                                    .add("testDesc")
                                    .add("------------------- End ---------------------")
                                    .add(menu + ln)
                                    .toString()
                    )
            );
        }
    }
}
