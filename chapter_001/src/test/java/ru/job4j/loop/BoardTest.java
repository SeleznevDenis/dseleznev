package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BoardTest {
    /**
     * Test
     * Тестирует построение шахматной доски 3*3.
     */
    @Test
    public void whenPaintBoardWithWidthThreeAndHeightThreeThenStringWithThreeColsAndThreeRows() {
       Board board = new Board();
       String result = board.paint(3, 3);
       final String line = System.getProperty("line.separator");
       String expected = String.format("x x%s x %sx x%s", line, line, line);
       assertThat(result, is(expected));
    }

    /**
     * Test
     * Тестирует построение шахматной доски 5*5.
     */
    @Test
    public void whenPaintBoardWithWidthFiveAndHeightFiveThenStringWithFiveColsAndFiveRows() {
        Board board = new Board();
        String result = board.paint(5, 5);
        final String line = System.getProperty("line.separator");
        String expected = String.format("x x x%s x x %sx x x%s x x %sx x x%s", line, line, line, line, line);
        assertThat(result, is(expected));
    }
}
