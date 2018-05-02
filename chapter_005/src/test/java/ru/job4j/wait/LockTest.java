package ru.job4j.wait;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        Lock lock = new Lock();
        Thread first = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unLock();
        });
        Thread second = new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unLock();
            lock.lock();
            lock.unLock();
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
        List<String> expectLog = new ArrayList<>(asList(
                "first: захватил lock", "second: ждет в методе lock",
                "first: разблокировал lock", "second: захватил lock",
                "second: разблокировал lock"
        ));
        assertThat(lock.getLog(), is(expectLog));
    }
}