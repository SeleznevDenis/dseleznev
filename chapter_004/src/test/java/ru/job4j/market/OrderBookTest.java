package ru.job4j.market;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test OrderBook.
 * @author Denis Seleznev
 * @version $Id$
 * @since 15.04.2018
 */
public class OrderBookTest {

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final String ln = System.lineSeparator();

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(stdout);
    }

    /**
     * Тест естественного сравнения заявок, и их отображения.
     */
    @Test
    public void ifAddOrdersToTreeSetOrdersShouldBeSorted() {
        Set<OrderBook> testSet = new TreeSet<>();
        testSet.add(new OrderBook(1, "gazprom", "add", "bid", 100, 1000));
        testSet.add(new OrderBook(2, "gazprom", "add", "bid", 10, 100));
        testSet.add(new OrderBook(3, "gazprom", "add", "bid", 11, 100));
        testSet.add(new OrderBook(4, "gazprom", "add", "ask", 13, 104));
        testSet.add(new OrderBook(5, "gazprom", "add", "ask", 14, 100));
        testSet.add(new OrderBook(6, "gazprom", "add", "ask", 15, 100));
        for (OrderBook item : testSet) {
            System.out.println(item);
        }
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringJoiner(ln)
                                .add("           15,00   100")
                                .add("           14,00   100")
                                .add("           13,00   104")
                                .add("   1000   100,00")
                                .add("    100    11,00")
                                .add("    100    10,00" + ln)
                                .toString()
                )
        );
    }

    /**
     * Тест метода equals.
     */
    @Test
    public void ifIdEqualsThenOrdersShouldBeEquals() {
        OrderBook test1 = new OrderBook(1, "gazprom", "delete", "bid", 10, 100);
        OrderBook test2 = new OrderBook(1, "gazprom", "add", "bid", 10, 100);
        assertThat(test1.equals(test2), is(true));
    }
}