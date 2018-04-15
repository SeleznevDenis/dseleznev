package ru.job4j.market;

import java.util.Objects;

/**
 * Заявка для банковского стакана.
 * @author Denis Seleznev
 * @version $Id$
 * since 15.04.2018
 */
public class OrderBook implements Comparable {

    private final int id;
    private final String book;
    private final String type;
    private final String action;
    private final double price;
    int volume;

    /**
     * @return вид заявки для отображения.
     */
    @Override
    public String toString() {
        String result;
        if (this.action.equals("bid")) {
            result = String.format("%7s%9.2f", this.volume, this.price);
        } else {
            result = String.format("%7s%9.2f%6s", " ", this.price, this.volume);
        }
        char[] charArray = result.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '.') {
                charArray[i] = ',';
            }
            result = new String(charArray);
        }
        return result;
    }

    /**
     * Конструктор, инициализирует заявку.
     * @param id идентификатор.
     * @param book эмитент.
     * @param type тип заявки.
     * @param action действие заявки.
     * @param price цена заявки.
     * @param volume количество акций.
     */
    public OrderBook(int id, String book, String type, String action, double price, int volume) {
        this.id = id;
        this.book = book;
        this.type = type;
        this.action = action;
        this.price = price;
        this.volume = volume;
    }

    /**
     * Сравнивает заявки по id
     * @param o объект сравнения.
     * @return результат сравнения.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderBook orderBook = (OrderBook) o;
        return id == orderBook.id;
    }

    /**
     * @return hashCode заявки по полю id.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Позволяет сортировать заявки в естественном порядке.
     * @param o объект сравнения.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(Object o) {
        OrderBook item = (OrderBook) o;
        int result;
        if (!this.action.equals(item.action)) {
            result = this.action.compareTo(item.action);
        } else if (this.price != item.price) {
            result = Double.compare(item.price, this.price);
        } else {
            result = Integer.compare(this.id, item.id);
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public String getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }
}

