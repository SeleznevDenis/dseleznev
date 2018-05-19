package ru.job4j.deadlock;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CyclicBarrier;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 19.05.2018
 */
public class DeadLockTest {

    /**
     * Запуск взаимоблокировки двух объектов.
     */
    @Ignore
    @Test
    public void deadLock() {
        CyclicBarrier barrier = new CyclicBarrier(2);
        DeadLock A = new DeadLock(barrier);
        DeadLock B = new DeadLock(barrier);
        A.setOther(B);
        B.setOther(A);
        Thread first = new Thread(A);
        Thread second = new Thread(B);
        first.start();
        second.start();
        try {
            second.join();
            first.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}