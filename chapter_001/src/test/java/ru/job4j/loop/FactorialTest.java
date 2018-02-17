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
        Factorial factorial = new Factorial();
        int result = factorial.calc(5);
        assertThat(result, is(120));
    }

    /**
     * Test factorialOfNumberOne.
     * Тестирует метод calc класса Factorial с параметром 1.
     */
    @Test
    public void factorialOfNumberOne() {
        Factorial factorial = new Factorial();
        int result = factorial.calc(1);
        assertThat(result, is(1));
    }
}
