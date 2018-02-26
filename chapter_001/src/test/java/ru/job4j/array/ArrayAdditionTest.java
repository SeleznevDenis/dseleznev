package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test ArrayAddition
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArrayAdditionTest {

    /**
     * Test addition если первый массив длиннее второго.
     */
    @Test
    public void arrayAdditionIfArrayOneMoreThenArrayTwo() {
        int[] array1 = {1, 2, 50, 180, 290, 300, 1024};
        int[] array2 = {54, 66, 75, 84};
        int[] except = {1, 2, 50, 54, 66, 75, 84, 180, 290, 300, 1024};
        ArrayAddition array = new ArrayAddition();
        int[] result = array.addition(array1, array2);
        assertThat(result, is(except));
    }

    /**
     * Test addition если второй массив длиннее первого.
     */
    @Test
    public void arrayAdditionIfArrayTwoMoreThenArrayOne() {
        int[] array1 = {6, 100};
        int[] array2 = {8, 45, 55, 250};
        int[] except = {6, 8, 45, 55, 100, 250};
        ArrayAddition array = new ArrayAddition();
        int[] result = array.addition(array1, array2);
        assertThat(result, is(except));
    }

    /**
     * Test addition если первый массив содержит все нули.
     */
    @Test
    public void arrayAdditionIfArrayOneHaveAllZero() {
        int[] array1 = {0, 0, 0, 0};
        int[] array2 = {1, 4, 6};
        int[] except = {0, 0, 0, 0, 1, 4, 6};
        ArrayAddition array = new ArrayAddition();
        int[] result = array.addition(array1, array2);
        assertThat(result, is(except));
    }
}
