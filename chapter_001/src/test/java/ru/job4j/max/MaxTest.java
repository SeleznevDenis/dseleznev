package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test
 * Тестирует объект класса Max.
 */
public class MaxTest {
    Max maxim = new Max();
    /**
     * Test whenFirstLessSecond.
     * Тестирует метод max.
     * Если первое число меньше второго,
     * метод max должен вернуть второе число.
     */
    @Test
    public void whenFirstLessSecond() {
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
        int result = maxim.max(4, 4);
        assertThat(result, is(4));
    }

    /**
     * Test whenFirstOfThreeNumbersMore.
     * Тестирует метод Max с тремя параметрами.
     * Max должен вернуть первое число.
     */
    @Test
    public void whenFirstOfThreeNumbersMore() {
        int result = maxim.max(6, 4, 2);
        assertThat(result, is(6));
    }
    /**
     * Test whenFirstOfThreeNumbersMore.
     * Тестирует метод Max с тремя параметрами.
     * Max должен вернуть второе число.
     */
    @Test
    public void whenSecondofThreeNumbersMore() {
        int result = maxim.max(3, 10, 1);
        assertThat(result, is(10));
    }
    /**
     * Test whenFirstOfThreeNumbersMore.
     * Тестирует метод Max с тремя параметрами.
     * Max должен вернуть третье число.
     */
    @Test
    public void whenThirdOfThreeNumbersMore() {
        int result = maxim.max(4, 8, 15);
        assertThat(result, is(15));
    }
    /**
     * Test whenFirstOfThreeNumbersMore.
     * Тестирует метод Max с тремя равными параметрами.
     * Max должен вернуть число 2.
     */
    @Test
    public void whenThreeNumbersEquals() {
        int result = maxim.max(2, 2, 2);
        assertThat(result, is(2));
    }
}
