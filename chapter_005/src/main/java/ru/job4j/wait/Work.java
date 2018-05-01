package ru.job4j.wait;

/**
 * Пример объекта, для обработки Пулом Потоков.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 01.05.2018
 */
public class Work {

    private String print;

    Work(String string) {
        this.print = string;
    }

    /**
     * Действие для выполнение threadPool.
     * Цикл введен для нагрузки системы, с целью наглядного тестирования threadPool.
     */
    public void doIt() {
        int e = 1;
        for (int i = 0; i < 10000000; i++) {
            e = i + e * i + (int) Math.ceil(Math.sqrt((double) i));
        }
        System.out.printf("%s do: %s \n\r", Thread.currentThread().getName(), this.print);
    }
}
