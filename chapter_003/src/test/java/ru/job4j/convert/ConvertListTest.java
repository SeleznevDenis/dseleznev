package ru.job4j.convert;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev(d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ConvertListTest {
    /**
     * Test convert toList.
     */
    @Test
    public void convertTwoDimensionalArrayToArrayList() {
        ConvertList testConvert = new ConvertList();
        List<Integer> result = testConvert.toList(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 8}
        });
        List<Integer> expect = new ArrayList<>(asList(1, 2, 3, 4, 5, 6, 7, 8, 8));
        assertThat(result, is(expect));
    }

    /**
     * Test convert toArray.
     */
    @Test
    public void convertArrayListToTwoDimensionalArray() {
        ConvertList testConvert = new ConvertList();
        int[][] result = testConvert.toArray(new ArrayList<>(asList(1, 2, 3, 4, 5, 6, 7)), 3);
        int[][] expect = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 0}
        };
        assertThat(result, is(expect));
    }
}
