package ru.job4j.calculator;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 *Test Calculator
 *
 *@author Denis Seleznev (d.selezneww@mail.ru)
 *@version $Id$
 *@since 0.1
 */
public class CalculatorTest {

    private Calculator calc = new Calculator();
    /**
     * Test add.
     */
    @Test
    public void whenAddOnePlusOneThenTwo() {
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
    /**
     * Test subtract.
     */
    @Test
    public void whenSubtractThreePlusOneThenTwo() {
        calc.subtract(3D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
    /**
     * Test div.
     */
    @Test
    public void whenDivSixPlusTwoThenThree() {
        calc.div(6D, 2D);
        double result = calc.getResult();
        double expected = 3D;
        assertThat(result, is(expected));
    }
    /**
     * Test multiple.
     */
    @Test
    public void whenMultipleTwoPlusTwoThenFour() {
        calc.multiple(2D, 2D);
        double result = calc.getResult();
        double expected = 4D;
        assertThat(result, is(expected));
    }
}
