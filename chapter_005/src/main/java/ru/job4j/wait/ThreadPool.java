package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Thread Pool.
 * @author Denis Seleznev
 * @version $Id$
 * @since 01.05.2018
 */
@ThreadSafe
public class ThreadPool {
    /**
     * Очередь задач.
     */
    @GuardedBy("this")
    private Queue<Work> tasksQueue = new LinkedList<>();

    /**
     * Лимит очереди задач.
     */
    private static final int TASKS_QUEUE_LIMIT = 5;

    /**
     * Количество ядер системы.
     */
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    /**
     * Состояние thread pool.
     */
    private boolean running;

    /**
     * Массив используемых потоков.
     */
    private Thread[] allThreads;

    /**
     * Вводит задачу в очередь.
     * @param work задача для обработки.
     */
    public synchronized void add(Work work) {
        while (tasksQueue.size() >= TASKS_QUEUE_LIMIT) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.tasksQueue.add(work);
        this.notify();
    }

    /**
     * @return задача для обработки.
     */
    private synchronized Work getAJob() {
        Work result;
        while (tasksQueue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = this.tasksQueue.poll();
        this.notify();
        return result;
    }

    /**
     * Запускает потоки.
     */
    public void runningThreads() {
        this.allThreads = new Thread[CORES];
        this.running = true;
        for (int i = 0; i < CORES; i++) {
            this.allThreads[i] = new Thread(() -> {
                boolean queueIsEmpty = true;
                while (!queueIsEmpty || this.running) {
                    Work job = getAJob();
                    if (job != null) {
                        job.doIt();
                    }
                    synchronized (this) {
                        queueIsEmpty = this.tasksQueue.isEmpty();
                    }
                }
            });
            this.allThreads[i].start();
        }
    }

    /**
     * @return массив потоков, используемых threadPool.
     */
    public Thread[] getThreads() {
        return this.allThreads;
    }

    /**
     * Останавливает работу threadPool, когда очередь задач опустеет.
     */
    public void stopThreads() {
        this.running = false;
    }
}
