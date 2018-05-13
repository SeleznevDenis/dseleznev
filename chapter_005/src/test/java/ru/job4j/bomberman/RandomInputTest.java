package ru.job4j.bomberman;

import org.junit.Test;
import ru.job4j.wait.SimpleBlockingQueue;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test RandomInput.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 13.05.2018
 */
public class RandomInputTest {

    @Test
    public void randomInputMustNotExceedTheLimits() {
        SimpleBlockingQueue<Cell> result = new SimpleBlockingQueue<>();
        RandomInput testInput = new RandomInput(result, 50);
        Thread input = new Thread(testInput);
        input.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        input.interrupt();
        try {
            input.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!result.isEmpty()) {
            Cell show = result.poll();
            assertThat(show.getRow() >= -1 && show.getRow() <= 1, is(true));
            assertThat(show.getColumn() >= -1 && show.getColumn() <= 1, is(true));
        }
    }
}