package ru.job4j.paint;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Square implements Shape {

    /**
     * Формирует строку посредством StringBuilder'а содержащую рисунок
     * квадрата в псевдографике.
     * @return сформированную строку.
     */
    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("†††††††††");
        pic.append("†     †");
        pic.append("†     †");
        pic.append("†††††††††");
        return pic.toString();
    }
}
