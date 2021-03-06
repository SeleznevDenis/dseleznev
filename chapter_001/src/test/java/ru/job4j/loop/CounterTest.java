package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CounterTest {
    /**
     * Test whenSumEvenNumbersFromOneToTenThenThirty.
     * Тестирует метод add класса Counter
     */
    @Test
    public void whenSumEvenNumbersFromOneToTenThenThirty() {
        Counter sumSquares = new Counter();
        int result = sumSquares.add(1, 10);
        assertThat(result, is(30));
    }
}
