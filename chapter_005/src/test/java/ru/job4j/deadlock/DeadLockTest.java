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
        DeadLock a = new DeadLock(barrier);
        DeadLock b = new DeadLock(barrier);
        a.setOther(b);
        b.setOther(a);
        Thread first = new Thread(a);
        Thread second = new Thread(b);
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