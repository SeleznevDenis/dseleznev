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
    public class RaceCondition {
        /**
         * Счетчик.
         */
        int counter;

        /**
         * Флаг запуска первого потока.
         */
        boolean firstFlag;

        /**
         * Флаг запуска второго потока.
         */
        boolean secondFlag;

        /**
         * Устанавливает значение заданного флага true.
         * Дожидается пока второй поток установит свой флаг,
         * И начинает инкрементировать счетчик counter.
         * @param flag заданный для установки флаг.
         */
        public void setFlag(String flag) {
            synchronized (this) {
                if (flag.equals("firstFlag")) {
                    this.firstFlag = true;
                    System.out.printf("%s установил firstFlag%n", Thread.currentThread().getName());
                } else if (flag.equals("secondFlag")) {
                    this.secondFlag = true;
                    System.out.printf("%s установил secondFlag%n", Thread.currentThread().getName());
                }
                this.notify();
                while (!firstFlag || !secondFlag) {
                    try {
                        System.out.printf("%s ждёт активацию флага%n", Thread.currentThread().getName());
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            increment();
        }

        /**
         * Инкрементирует счетчик count 10 раз.
         */
        private void increment() {
            for (int i = 0; i < 10; i++) {
                System.out.printf("%s инкрементирует %s%n", Thread.currentThread().getName(), ++this.counter);
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
