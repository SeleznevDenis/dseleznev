package ru.job4j.bomberman;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.Random;

/**
 * Описывает случайный ввод команд для персонажа игры бомбермен.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 13.05.2018
 */
public class RandomInput implements Runnable {
    /**
     * Хранит ссылку на очередь для ввода комманд.
     */
    private final SimpleBlockingQueue<Cell> currentQueue;

    /**
     * Временная задержка между коммандами.
     */
    private final int timeDelay;

    /**
     * Инициализирует очередь для ввода команд и временную задержку между коммандами.
     * @param currentQueue очередь для ввода случайных команд.
     * @param timeDelay временная задержка между коммандами.
     */
    RandomInput(SimpleBlockingQueue<Cell> currentQueue, int timeDelay) {
        this.currentQueue = currentQueue;
        this.timeDelay = timeDelay;
    }

    /**
     * Расчитывает случайный сдвиг в одно из 8 положений вокруг ячейки.
     * @return Cell содержащую комманду - случайный сдвиг координат.
     */
    private Cell randomStep() {
        int randomRowStep = new Random().nextInt(3) - 1;
        int randomColumnStep = new Random().nextInt(3) - 1;
        return new Cell(randomRowStep, randomColumnStep);
    }

    /**
     * Запускает случайный ввод команд.
     * Останавливается при выставлении флага interrupt.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            this.currentQueue.offer(randomStep());
            try {
                Thread.sleep(timeDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
