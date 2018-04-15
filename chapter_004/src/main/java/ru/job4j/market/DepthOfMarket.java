package ru.job4j.market;

import java.util.*;
import java.util.function.BiPredicate;

/**
 * Биржевые Стаканы.
 * @author Denis Seleznev
 * @version $Id$
 * @since 15.04.2018
 */
public class DepthOfMarket {

    private Map<String, TreeSet<OrderBook>> mainStorage = new HashMap<>();
    private final static String BID = "bid";
    private final static String ASK = "ask";
    private final static String ADD = "add";
    private final static String DELETE = "delete";

    /**
     * Вводит заявку в систему.
     * @param order заявка для ввода в систему.
     * @return успешность операции.
     */
    public boolean putOrder(OrderBook order) {
        boolean done = true;
        if (order.getType().equals(ADD)) {
            addOrder(order);
        } else if (order.getType().equals(DELETE)) {
            String book = order.getBook();
            if (this.mainStorage.containsKey(book)) {
                done = this.mainStorage.get(book).remove(order);
            }
        }
        return done;
    }

    /**
     * Добавляет заявку в хранилище, автоматически
     * вычитает объемы у заявок с противоположным
     * действием и удаляет заявки с пустым объемом.
     * @param order заявка для добавления.
     */
    private void addOrder(OrderBook order) {
        String orderBook = order.getBook();
        if (!this.mainStorage.containsKey(orderBook)) {
            this.mainStorage.put(orderBook, new TreeSet<>());
        }
        List<OrderBook> ordersForDelete = new ArrayList<>();
        String oppositeAction;
        BiPredicate<Double, Double> predict;
        if (order.getAction().equals(BID)) {
            oppositeAction = ASK;
            predict = (x, y) -> x <= y;
        } else {
            oppositeAction = BID;
            predict = (x, y) -> x >= y;
        }
        for (OrderBook currentBook : this.mainStorage.get(orderBook)) {
            if (currentBook.getAction().equals(oppositeAction)
                    && predict.test(currentBook.getPrice(), order.getPrice())) {
                int difference = Math.min(currentBook.getVolume(), order.getVolume());
                currentBook.volume -= difference;
                order.volume -= difference;
                if (currentBook.volume == 0) {
                    ordersForDelete.add(currentBook);
                }
            }
        }
        if (order.volume > 0) {
            this.mainStorage.get(orderBook).add(order);
        }
        for (OrderBook currentOrder : ordersForDelete) {
            this.mainStorage.get(orderBook).remove(currentOrder);
        }
    }

    /**
     * Возвращает Set заявок, находящихся в стакане.
     * Заявки с одинаковой ценой объединяются, для отображения.
     * @param book эмитент.
     * @return Множество заявок для отображения.
     */
    public Set<OrderBook> showOrders(String book) {
        Set<OrderBook> setForShow = new LinkedHashSet<>();
        if (!this.mainStorage.get(book).isEmpty()) {
            OrderBook orderForAddition = this.mainStorage.get(book).pollFirst();
            for (OrderBook currentOrder : this.mainStorage.get(book)) {
                if (currentOrder.getType().equals(orderForAddition.getType())
                        && currentOrder.getPrice() == orderForAddition.getPrice()) {
                    orderForAddition.volume += currentOrder.volume;
                } else {
                    setForShow.add(orderForAddition);
                    orderForAddition = currentOrder;
                }
            }
            setForShow.add(orderForAddition);
        }
        return setForShow;
    }
}
