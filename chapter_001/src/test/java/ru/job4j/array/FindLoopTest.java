package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test FindLoop
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */

public class FindLoopTest {
    /**
     * Test indexOf если заданное число есть в массиве под индексом 8.
     */
    @Test
    public void ifMassivOfSixElementsFindIndexContainsEight() {
        int[] data = {1, 15, 14, 8, 22};
        FindLoop findloop = new FindLoop();
        int result = findloop.indexOf(data, 8);
        assertThat(result, is(3));
    }

    /**
     * Test indexOf если заданного числа нет в массиве.
     */
    @Test
    public void ifMassivHasNotAFindNomber() {
        int[] data = {1, 3, 4};
        FindLoop findloop = new FindLoop();
        int result = findloop.indexOf(data, 5);
        assertThat(result, is(-1));
    }
}
