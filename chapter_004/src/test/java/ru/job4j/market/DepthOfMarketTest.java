package ru.job4j.market;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Test DepthOfMarket
 * @author Denis Seleznev
 * @version $Id$
 * @since 15.04.2018
 */
public class DepthOfMarketTest {

    private DepthOfMarket testMarket;

    @Before
    public void setUp() {
        testMarket = new DepthOfMarket();
    }

    @Test
    public void ifAddOrdersThenShowOrdersReturnCombinedVolumeAtSamePrice() {
        testMarket.putOrder(new OrderBook(1, "gazprom", "add", "ask", 16, 10));
        testMarket.putOrder(new OrderBook(2, "gazprom", "add", "ask", 17, 20));
        testMarket.putOrder(new OrderBook(3, "gazprom", "add", "ask", 16, 30));
        Iterator<OrderBook> it = testMarket.showOrders("gazprom").iterator();
        assertThat(it.next().getVolume(), is(20));
        assertThat(it.next().getVolume(), is(40));
    }

    @Test
    public void ifDeleteOneOrderThenShowOrdersReturnWithoutHim() {
        testMarket.putOrder(new OrderBook(1, "gazprom", "add", "ask", 16, 30));
        testMarket.putOrder(new OrderBook(2, "gazprom", "add", "ask", 16, 30));
        testMarket.putOrder(new OrderBook(3, "gazprom", "add", "ask", 16, 30));
        testMarket.putOrder(new OrderBook(1, "gazprom", "delete", "ask", 16, 30));
        Iterator<OrderBook> it = testMarket.showOrders("gazprom").iterator();
        assertThat(it.next().getVolume(), is(60));
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void testAnnihilationOfAllOrders() {
        testMarket.putOrder(new OrderBook(1, "gazprom", "add", "ask", 15, 30));
        testMarket.putOrder(new OrderBook(2, "gazprom", "add", "ask", 16, 30));
        testMarket.putOrder(new OrderBook(3, "gazprom", "add", "bid", 17, 30));
        testMarket.putOrder(new OrderBook(4, "gazprom", "add", "bid", 16, 30));
        assertThat(testMarket.showOrders("gazprom").isEmpty(), is(true));
    }

    @Test
    public void ifAddOppositionOrdersShouldBeSubtractionVolumes() {
        testMarket.putOrder(new OrderBook(1, "gazprom", "add", "ask", 15, 31));
        testMarket.putOrder(new OrderBook(2, "gazprom", "add", "ask", 15, 30));
        testMarket.putOrder(new OrderBook(3, "gazprom", "add", "bid", 15, 30));
        testMarket.putOrder(new OrderBook(4, "gazprom", "add", "bid", 15, 30));
        Iterator<OrderBook> it = testMarket.showOrders("gazprom").iterator();
        assertThat(it.next().getVolume(), is(1));
        assertThat(it.hasNext(), is(false));
    }
}