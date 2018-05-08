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
     * Тест проблемы доступа к общей переменной. (RaceCondition)
     * Создается два потока, использующих объект CommonVariable
     * При запуске потоков, производятся изменения общей переменной таким образом,
     * Чтобы при вмешательстве в процессе вычислений переменной другого потока,
     * значение её становилось непредсказуемым. При запуске в 1 поток, значение переменной всегда 1.
     */
    @Test
    public void raceConditionTestFirst() {
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
     * для увеличения вероятности повторяемости проблемы: 2 потока, входят в метод setFlag,
     * где каждый устанавливает свой флаг.
     * Когда оба флага установлены потоки с большей вероятностью одновременно начинают
     * инкрементировать счетчик и выводить результат в консоль.
     */
    @Test
    public void raceConditionalTestSecond() {
        MTErrorsExample.RaceCondition test = new MTErrorsExample().new RaceCondition();
        Thread first = new Thread(() -> {
            test.setFlag("firstFlag");
            System.out.println("Первый тред закончил работу");
        });
        Thread second = new Thread(() -> {
            test.setFlag("secondFlag");
            System.out.println("Второй тред закончил работу");
        });
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Тест проблемы взаимной блокировки.
     * Первый поток входит в синхронизованный метод и блокирует первый объект.
     * Второй поток входит в синхронизованный метод и блокирует второй объект.
     * Затем потоки ждут некоторое время, для увеличения вероятности повторяемости результата взаимной блокировки.
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













