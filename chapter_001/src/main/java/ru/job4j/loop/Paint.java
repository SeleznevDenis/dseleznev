package ru.job4j.loop;

import java.util.function.BiPredicate;
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
     * loopBy
     * @param height высота рисунка из псевдографики.
     * @param weight ширина рисунка из псевдографики.
     * @param predict условие прорисовки рисунка.
     * @return Возвращает рисунок из псевдографики в строке в зависимости от условия (row, column).
     */
    private String loopBy(int height, int weight, BiPredicate<Integer, Integer> predict) {
      StringBuilder screen = new StringBuilder();
      for (int row = 0; row != height; row++) {
          for (int column = 0; column != weight; column++) {
              if (predict.test(row, column)) {
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
     * rightTrl
     * @param height высота пирамиды.
     * @return правую часть рисунка пирамиды из псевдографики в строке.
     */
    public String rightTrl(int height) {
       return this.loopBy(
               height,
               height,
               (row, column) -> row >= column
       );
    }

    /**
     * leftTrl
     * @param height высота пирамиды
     * @return левую часть пирамиды из псвдографики в строке.
     */
    public String leftTrl(int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= height - column - 1
        );

    }

    /**
     * pyramid.
     * @param height высота пирамиды.
     * @return рисунок пирамиды из псевдографики в строке.
     */
    public String pyramid(int height) {
        return this.loopBy(
                height,
                2 * height - 1,
                (row, column) -> row >= height - column - 1 && row + height - 1 >= column
        );
    }
}


