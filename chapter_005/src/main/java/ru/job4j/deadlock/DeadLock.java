package ru.job4j.deadlock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Гарантированный DeadLock.
 * @author Denis Seleznev
 * @version $Id$
 * @since 19.05.2018
 */
public class DeadLock implements Runnable {
    /**
     * Хранит ссылку на другой объект DeadLock
     */
    private DeadLock other;
    /**
     * Хранит ссылку на барьер.
     */
    private final CyclicBarrier barrier;

    /**
     * Инициализирует барьер.
     * @param barrier барьер.
     */
    DeadLock(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    /**
     * Инициализирует другой объект DeadLock.
     * @param other другой DeadLock.
     */
    public void setOther(DeadLock other) {
        this.other = other;
    }

    /**
     * Выводит на печать фразу.
     * Метод никогда не будет вызван, потому, что объекты будут находиться
     * в состоянии взаимной блокировки.
     */
    private synchronized void lock() {
        System.out.println("Не будет напечатано");
    }

    /**
     * Захватывает монитор своего объекта, затем с помощью барьера, синхронизируется со вторым объектом и
     * когда оба объекта захватили свои мониторы, пытается вызвать метод lock другого объекта. Метод не будет вызван
     * так как оба объекта будут находиться в состоянии взаимоблокировки.
     */
    @Override
    public void run() {
        synchronized (this) {
            System.out.println(String.format(
                    "%s захватил монитор своего объекта", Thread.currentThread().getName()
            ));
            try {
                this.barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("Блокировка");
            this.other.lock();
        }
    }
}