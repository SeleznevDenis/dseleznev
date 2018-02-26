package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FactorialTest {
    /**
     * Test factorialOfNumberFive.
     * Тестирует метод calc класса Factorial с параметром 5.
     */
    @Test
    public void factorialOfNumberFive() {
        Factorial factor = new Factorial();
        int result = factor.calc(5);
        assertThat(result, is(120));
    }

    /**
     * Test factorialOfNumberOne.
     * Тестирует метод calc класса Factorial с параметром 1.
     */
    @Test
    public void factorialOfNumberOne() {
        Factorial factor = new Factorial();
        int result = factor.calc(1);
        assertThat(result, is(1));
    }
}
