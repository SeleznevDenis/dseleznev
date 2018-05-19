package ru.job4j.switcher;

import java.util.concurrent.Semaphore;

/**
 * Класс обертка над linker.
 * @author Denis Seleznev
 * @version $Id$
 * @since 19.05.2018
 */
public class Switcher {
    /**
     * Номер потока, который будет производить запись первым.
     */
    private final int numberOfFirstWriterThread;

    /**
     * Ссылка на первый поток.
     */
    private Thread first;

    /**
     * Ссылка на второй поток.
     */
    private Thread second;

    /**
     * Ссылка на объект с которым производится работа.
     */
    private final ConcatenationString string = new ConcatenationString();

    /**
     * Инициализирует номер первого записывающего потока.
     * @param numberOfFirstWriterThread номер первого записывающего потока.
     */
    Switcher(int numberOfFirstWriterThread) {
        this.numberOfFirstWriterThread = numberOfFirstWriterThread;
    }

    /**
     * Запускает свитчер.
     */
    public void startSwitching() {
        Semaphore firstSemaphore;
        Semaphore secondSemaphore;
        if (this.numberOfFirstWriterThread == 1) {
            firstSemaphore = new Semaphore(1);
            secondSemaphore = new Semaphore(0);
        } else {
            firstSemaphore = new Semaphore(0);
            secondSemaphore = new Semaphore(1);
        }
        this.first = new Thread(new Linker(firstSemaphore, secondSemaphore, 1, this.string));
        this.second = new Thread(new Linker(secondSemaphore, firstSemaphore, 2, this.string));
        first.start();
        second.start();
    }

    /**
     * Останавливает свитчер.
     * @return значение в рабочей строке.
     */
    public String stopSwitchingWithResult() {
        first.interrupt();
        second.interrupt();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.string.getString();
    }
}
