package ru.job4j.monitore;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 27.04.2018
 */
public class CountTest {

    /**
     * Поток, работающий со счетчиком.
     */
    private class ThreadCount extends Thread {
        private final Count count;

        /**
         * Инициализирует счетчик заданным.
         * @param count заданный счетчик
         */
        private ThreadCount(final Count count) {
            this.count = count;
        }

        /**
         * Инкрементирует счетчик.
         */
        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final Count count = new Count();
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(2));
    }
}