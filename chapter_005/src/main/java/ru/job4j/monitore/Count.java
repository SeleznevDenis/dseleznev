package ru.job4j.monitore;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Простой потокобезопасный счетчик.
 * @author Denis Seleznev
 * @version $Id$
 * @since 27.04.2018
 */
@ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

    /**
     * Инкрементирует счетчик.
     */
    public synchronized void increment() {
        this.value++;
    }

    /**
     * @return возвращает значение счетчика.
     */
    public synchronized int get() {
        return this.value;
    }
}
