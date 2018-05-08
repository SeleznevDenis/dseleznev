package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

/**
 * Простой механизм блокировки Lock.
 * @author Denis Seleznev
 * @version $Id$
 * @since 02.05.2018
 */
@ThreadSafe
public class Lock {

    /**
     * Хранит ссылку на поток, который захватил lock.
     */
    @GuardedBy("this")
    private Thread lockOwner;

    /**
     * Блокирует lock, если он свободен. Если другой поток владеет lock, ждёт освобождения.
     */
    public synchronized void lock() {
        while (this.lockOwner != null && !this.lockOwner.equals(Thread.currentThread())) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.lockOwner = Thread.currentThread();
    }

    /**
     * Разблокирует lock, если текущий поток владеет Lock.
     */
    public synchronized void unLock() {
        if (this.lockOwner != null && this.lockOwner.equals(Thread.currentThread())) {
            this.lockOwner = null;
            this.notify();
        }
    }
}
