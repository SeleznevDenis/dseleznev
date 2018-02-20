package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * SquareTest
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class SquareTest {
    /**
     * Test calculate.
     */
    @Test
    public void ifBoundEqualsFive() {
        Square square = new Square();
        int[] result = square.calculate(5);
        int[] expect = {1, 4, 9, 16, 25};
        assertThat(result, is(expect));
    }
}
