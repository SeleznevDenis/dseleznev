package ru.job4j.loop;

/**
 * Объект класса Board строит строки с рисунком
 * шахматной доски в псевдографике с заданной длинной полей.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Board {
    /**
     * paint
     * @param width высота шахматной доски.
     * @param height ширина шахматной доски.
     * @return строки с рисунком шахматной доски в псевдографике.
     */
    public String paint(int width, int height) {
        StringBuilder screen = new StringBuilder();
        String ln = System.lineSeparator();
        for (int currentHeight = 0; currentHeight < height; currentHeight++) {
            for (int currentWidth = 0; currentWidth < width; currentWidth++) {
                if ((currentWidth + currentHeight) % 2 == 0) {
                    screen.append("x");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(ln);
        }
        return screen.toString();
    }
}
