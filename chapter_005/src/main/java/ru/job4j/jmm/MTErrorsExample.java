package ru.job4j.jmm;

/**
 * Класс содержащий вложенные классы - примеры
 * ошибок при использовании многопоточности.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 26.04.2018
 */
public class MTErrorsExample {

    /**
     * Иллюстрирует проблему "Состояние гонки".
     */
    public class RaceCondition implements Runnable {

        int counter;
        int increaseValue;

        public RaceCondition(int counter, int increaseValue) {
            this.counter = counter;
            this.increaseValue = increaseValue;
        }

        /**
         * При параллельной работе двух потоков с использованием этого метода, у разных объектов:
         * Очередность вывода значения счетчика в консоль непредсказуема.
         */
        @Override
        public void run() {
            while (this.counter < 100) {
                System.out.println(this.counter);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.counter += increaseValue;
            }
        }
    }

    /**
     * Иллюстрирует проблему "Взаимная блокировка".
     */
    public class DeadLock implements Runnable {

        /**
         * Ссылка на другой объект класса.
         */
        DeadLock other;

        /**
         * Инициализирует other.
         * @param other ссылка на другой объект класса.
         */
        DeadLock(DeadLock other) {
            this.other = other;
        }

        DeadLock() { }

        /**
         * Блокирует объект вызвавший этот метод, затем
         * выводит в консоль имя потока, зашедшего в метод, затем ждет.
         * Пытается вызвать метод lock другого объекта класса.
         * Если другой объект класса заблокирован и в параллельном потоке объект other пытается вызвать
         * метод lock текущего объекта - возникает состояние взаимной блокировки.
         * @param other ссылка на другой объект класса.
         */
        public synchronized void dead(DeadLock other) {
            System.out.println(Thread.currentThread().getName() + "В методе dead");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "пытается вызвать метод lock");
            other.lock();
        }

        public synchronized void lock() {
            System.out.println("Внутри метода lock");
        }

        @Override
        public void run() {
            dead(other);
        }
    }
    /**
     * Иллюстрирует проблему неблокируемого доступа к общему ресурсу.
     */
    public class CommonVariable implements Runnable {

        int counter = 1;

        /**
         * Умножает счетчик counter на 10, затем ожидает и вычитает из счетчика 9.
         * При использовании кода в одном потоке, счетчик всегда 1 после выполнения метода.
         * При использовании в несколько потоков, значение счетчика непредсказуемо.
         */
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                this.counter *= 10;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.counter -= 9;
            }
        }
    }
}
