package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Bounded blocked queue.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 30.04.2018
 * @param <T> Параметризованный тип очереди.
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final static int QUEUE_LIMIT = 10;

    /**
     * Вводит объект в конец очереди. Если очередь заполнена - ждёт освобождения.
     * @param value объект для ввода в очередь.
     */
    public synchronized void offer(T value) {
        while (this.queue.size() >= QUEUE_LIMIT) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.queue.offer(value);
        this.notify();

    }

    /**
     * Возвращает и удаляет объект из очереди. Если очередь пустая - ждёт.
     * @return объект из головы очереди.
     */
    public synchronized T poll() {
        while (this.queue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
        return this.queue.poll();
    }
}
