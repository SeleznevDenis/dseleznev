package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    private String menu = new StringBuilder()
                                  .append("Menu:\r\n")
                                  .append("0. Add new item\r\n")
                                  .append("1. Show all items\r\n")
                                  .append("2. Edit item\r\n")
                                  .append("3. Delete item\r\n")
                                  .append("4. Find item by id\r\n")
                                  .append("5. Find items by name\r\n")
                                  .append("6. Exit prigram\r\n")
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
    public void backOutput() {
        System.setOut(stdout);
    }

    /**
     * Test createItem.
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll()[0].getName(), is("test name"));
    }

    /**
     * Test editItem.
     */
    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item());
        Input input = new StubInput(new String[]{"2", item.getId(), "Y", "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is("test name"));
    }

    /**
     * Test deleteItem.
     */
    @Test
    public void whenDeleteItemThenItemNull() {
        Tracker tracker = new Tracker();
        Item checked = tracker.add(new Item());
        Input input = new StubInput(new String[]{"3", checked.getId(), "Y", "6"});
        new StartUI(input, tracker).init();
        assertNull(tracker.findById(checked.getId()));
    }

    /**
     * Test showAllItems.
     */
    @Test
    public void whenShowAllItemsThenOutputAllItems() {
        Tracker tracker = new Tracker();
        Item first = tracker.add(new Item("test1", "testDesc1", 10L));
        Item second = tracker.add(new Item("test2", "testDesc2", 11L));
        first.setId("1");
        second.setId("2");
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("---------------- All Items ------------------\r\n")
                                .append("1 | test1 | Thu Jan 01 05:00:00 YEKT 1970\r\n")
                                .append("testDesc1\r\n")
                                .append("2 | test2 | Thu Jan 01 05:00:00 YEKT 1970\r\n")
                                .append("testDesc2\r\n")
                                .append("------------------- End ---------------------\r\n")
                                .append(menu)
                                .toString()
                )
        );
    }

    /**
     * Test findById.
     */
    @Test
    public void whenFindByIdTestItemThenOutputTestItem() {
        Tracker tracker = new Tracker();
        Item checked = tracker.add(new Item("test", "testDesc", 10L));
        checked.setId("1");
        Input input = new StubInput(new String[]{"4", checked.getId(), "n", "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("---------------- Show item ------------------\r\n")
                                .append("Selected item : \r\n")
                                .append("1 | test | Thu Jan 01 05:00:00 YEKT 1970\r\n")
                                .append("testDesc\r\n")
                                .append("---------------- Show item end --------------\r\n")
                                .append(menu)
                                .toString()
                )
        );
    }

    /**
     * Test findByName.
     */
    @Test
    public void whenFindByNameTestThenOutputTestItem() {
        Tracker tracker = new Tracker();
        Item checked = tracker.add(new Item("test", "testDesc", 10L));
        checked.setId("1");
        Input input = new StubInput(new String[]{"5", "test", "6"});
        new StartUI(input, tracker).init();
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("----------------Find items by name-----------\r\n")
                                .append("1 | test | Thu Jan 01 05:00:00 YEKT 1970\r\n")
                                .append("testDesc\r\n")
                                .append("------------------- End ---------------------\r\n")
                                .append(menu)
                                .toString()
                )
        );
    }
}
