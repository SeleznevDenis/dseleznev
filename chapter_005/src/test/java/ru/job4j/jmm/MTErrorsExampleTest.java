package ru.job4j.jmm;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Запускает тесты проблем многопоточности.
 * @author Denis Seleznev
 * @version $Id$
 * @since 26.04.2018
 */
public class MTErrorsExampleTest {

    /**
     * Тест проблемы доступа к общей переменной.
     * Создается два потока, использующих объект CommonVariable
     * При запуске потоков, производятся изменения общей переменной таким образом,
     * Чтобы при вмешательстве в процессе вычислений переменной другого потока,
     * значение её становилось непредсказуемым. При запуске в 1 поток, значение переменной всегда 1.
     */
    @Ignore
    @Test
    public void commonVariableTest() {
        MTErrorsExample.CommonVariable test = new MTErrorsExample().new CommonVariable();
        Thread first = new Thread(test);
        Thread second = new Thread(test);
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(test.counter);
    }

    /**
     * Тест проблемы "Состояние гонки".
     * 2 потока выводят значение счетчика.
     * 1 поток с нуля прибавляя в каждом цикле 2.
     * 2 поток с единицы прибавляя в каждом цикле 2.
     * Если бы потоки работали параллельно с одной скоростью,
     * вывод на консоль был бы предсказуем.
     */
    @Ignore
    @Test
    public void raceConditionalTest() {
        new Thread(new MTErrorsExample().new RaceCondition(0, 2)).start();
        new Thread(new MTErrorsExample().new RaceCondition(1, 2)).start();
    }

    /**
     * Тест проблемы взаимной блокировки.
     * Первый поток входит в синхронизованный метод и блокирует первый объект.
     * Второй поток входит в синхронизованный метод и блокирует второй объект.
     * Затем потоки ждут некоторое время, для 100% повторяемости результата взаимной блокировки.
     * И пытаются безуспешно вызвать методы заблокированных объектов.
     */
    @Ignore
    @Test
    public void deadLockTest() {
        MTErrorsExample.DeadLock first = new MTErrorsExample().new DeadLock();
        MTErrorsExample.DeadLock second = new MTErrorsExample().new DeadLock(first);
        first.other = second;
        new Thread(first).start();
        new Thread(second).start();
    }
}













