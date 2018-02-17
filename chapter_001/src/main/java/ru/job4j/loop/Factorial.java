package ru.job4j.loop;

/**
 * Factorial. Объект данного класса
 * может рассчитать факториал от заданного числа.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Factorial {
    /**
     * calc.
     * @param n заданное число.
     * @return факториал заданного числа.
     */
    public int calc(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
