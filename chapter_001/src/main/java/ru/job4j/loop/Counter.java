package ru.job4j.loop;

/**
 * Counter. Объект данного класса считает сумму
 * целых четных чисел в заданном диапазоне.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Counter {
    /**
     * add работает с целыми числами.
     * @param start начало диапазона значений.
     * @param finish конец диапазона значений.
     * @return сумма четных чисел в заданном диапазоне включительно.
     */
    public int add(int start, int finish) {
        int count = 0;
      for (int i = start; i <= finish; i++) {
          if (i % 2 == 0) {
              count += i;
          }
      }
      return count;
    }
}
