package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * MatrixTest
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class MatrixTest {
    /**
     * Test multiple
     */
    @Test
    public void ifSizeOfMultipledTableEqualsThree() {
        Matrix matrix = new Matrix();
        int[][] result = matrix.multiple(3);
        int[][] expept = {
                {1, 2, 3},
                {2, 4, 6},
                {3, 6, 9}
        };
        assertThat(result, is(expept));
    }
}
