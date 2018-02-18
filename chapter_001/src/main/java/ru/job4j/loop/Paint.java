package ru.job4j.loop;

/**
 * Объект класса Paint может построить
 * пирамиду из псевдографики в консоли,
 * а также левую либо правую части пирамиды.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Paint {
    /**
     * rightTrl
     * @param height высота пирамиды.
     * @return правую часть рисунка пирамиды из псевдографики в строке.
     */
    public String rightTrl(int height) {
        StringBuilder screen = new StringBuilder();
        int weight = height;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (row >= column) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());

        }
        return screen.toString();
    }

    /**
     * leftTrl
     * @param height высота пирамиды
     * @return левую часть пирамиды из псвдографики в строке.
     */
    public String leftTrl(int height) {
        StringBuilder screen = new StringBuilder();
        int weight = height;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (row >= height - column - 1) {
                    screen.append("^");
                } else {
                        screen.append(" ");
                    }
                }
                screen.append(System.lineSeparator());
            }
            return screen.toString();
    }

    /**
     * pyramid.
     * @param height высота пирамиды.
     * @return рисунок пирамиды из псевдографики в строке.
     */
    public String pyramid(int height) {
        StringBuilder screen = new StringBuilder();
        int weight = 2 * height - 1;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (row >= height - column - 1 && row + height - 1 >= column) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }
}


