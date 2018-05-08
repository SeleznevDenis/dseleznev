package ru.job4j.wait;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test Lock.
 * @author Denis Seleznev
 * @version $Id$
 * @since 02.05.2018
 */
public class LockTest {

    @Test
    public void lockTest() {
        ConcurrentLinkedQueue<String> result = new ConcurrentLinkedQueue<>();
        Lock lock = new Lock();
        Thread first = new Thread(() -> {
            lock.lock();
            result.add("first get lock");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unLock();
            result.add("first unlock lock");
        });
        Thread second = new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unLock();
            lock.lock();
            result.add("second get lock");
            lock.unLock();
            result.add("second unlock lock");
        });
        first.setName("first");
        second.setName("second");
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] arr = new String[4];
        result.toArray(arr);
        assertThat(arr, is(new String[]{
                "first get lock", "first unlock lock", "second get lock", "second unlock lock"
        }));
    }
}