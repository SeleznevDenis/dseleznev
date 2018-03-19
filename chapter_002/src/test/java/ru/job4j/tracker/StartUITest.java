package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        assertThat(tracker.findAll().get(0).getName(), is("test name"));
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
