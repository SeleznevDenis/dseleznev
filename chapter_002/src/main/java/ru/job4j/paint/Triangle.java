package ru.job4j.paint;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Triangle implements Shape {

    /**
     * Формирует строку посредством StrinBuilder'а содержащую
     * рисунок треугольника в псевдографике.
     * @return возвращает сформированную строку.
     */
    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("   †   ");
        pic.append("  †††  ");
        pic.append(" †††††† ");
        pic.append("†††††††††");
        return pic.toString();
    }
}
