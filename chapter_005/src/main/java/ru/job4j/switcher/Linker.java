package ru.job4j.switcher;

import java.util.concurrent.Semaphore;

/**
 * Объект производящий добавление чисел к ConcatenationString.
 */
public class Linker implements Runnable {
    /**
     * Объект с которым производится работа.
     */
    private final ConcatenationString string;
    /**
     * Семафор текущего потока.
     */
    private final Semaphore thisSemaphore;
    /**
     * Семафор другого потока.
     */
    private final Semaphore thatSemaphore;
    /**
     * Номер который записывает текущий поток.
     */
    private final int threadNumber;

    /**
     * Инициализирует одноименные поля:
     * @param thisSemaphore семафор текущего потока.
     * @param thatSemaphore семафор другого потока.
     * @param threadNumber число, которое записывает текущий поток.
     * @param string строка с которой производится работа.
     */
    Linker(Semaphore thisSemaphore, Semaphore thatSemaphore, int threadNumber, ConcatenationString string) {
        this.thisSemaphore = thisSemaphore;
        this.thatSemaphore = thatSemaphore;
        this.threadNumber = threadNumber;
        this.string = string;
    }

    /**
     * Запрашивает разрешение у текущего семафора, при получении разрешения
     * добавляет к рабочей строке string, 10 чисел threadNumber,
     * затем освобождает разрешение для семафора другого потока.
     * Работает в цикле по условию !interrupted().
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                this.thisSemaphore.acquire();
            } catch (InterruptedException e) {
                break;
            }
            for (int i = 0; i < 10; i++) {
                this.string.concatenate(this.threadNumber);
            }
            this.thatSemaphore.release();
        }
    }
}

