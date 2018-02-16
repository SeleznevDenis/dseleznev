package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test
 * Тестирует объект класса Max.
 */
public class MaxTest {
    /**
     * Test whenFirstLessSecond.
     * Тестирует метод max.
     * Если первое число меньше второго,
     * метод max должен вернуть второе число.
     */
    @Test
    public void whenFirstLessSecond() {
        Max maxim = new Max();
        int result = maxim.max(1, 2);
        assertThat(result, is(2));
    }

    /**
     * Test whenSecondLessFirst.
     * Тестирует метод max.
     * Если второе число меньше первого,
     * метод max должен вернуть первое число.
     */
    @Test
    public void whenSecondLessFirst() {
        Max maxim = new Max();
        int result = maxim.max(3, 2);
        assertThat(result, is(3));
    }

    /**
     * Test whenFirstEqualSecond.
     * Тестирует метод max.
     * Если первое число равно второму,
     * метод max должен вернуть первое число.
     */
    @Test
    public void whenFirstEqualSecond() {
        Max maxim = new Max();
        int result = maxim.max(4, 4);
        assertThat(result, is(4));
    }
}
