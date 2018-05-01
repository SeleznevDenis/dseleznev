package ru.job4j.wait;

import org.junit.Test;

/**
 * Test ThreadPool.
 * @author Denis Seleznev
 * @version $Id$
 * @since 01.05.2018
 */
public class ThreadPoolTest {

    /**
     * Поток main, запускает threadPool и вводит в него задачи,
     * затем дожидается окончания работы.
     */
    @Test
    public void testThreadPool() {
        ThreadPool testPool = new ThreadPool();
        testPool.runningThreads();
        for (int i = 0; i < 21; i++) {
            testPool.add(new Work(Integer.toString(i)));
        }
        testPool.stopThreads();
        for (Thread thread : testPool.getThreads()) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}