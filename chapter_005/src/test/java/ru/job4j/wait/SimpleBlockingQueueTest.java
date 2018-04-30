package ru.job4j.wait;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * SimpleBlockingQueue Producer Consumer Test.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 30.04.2018
 */
public class SimpleBlockingQueueTest {

    @Test
    public void simpleBlockingQueueIn2Threads() {
        Queue<Integer> result = new LinkedList<>();
        Queue<Integer> expect = new LinkedList<>();
        SimpleBlockingQueue<Integer> testQ = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                expect.offer(i);
                testQ.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                result.offer(testQ.poll());
            }
        });
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(result, is(expect));
    }
}