package ru.job4j.bomberman;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Board Test.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 10.05.2018
 */
public class BoardTest {

    @Test
    public void ifInitBoardThenBoardDANullElements() {
        Board testBoard = new Board(10);
        testBoard.init(0);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertNotNull(testBoard.getLock(new Cell(i, j)));
            }
        }
    }

    @Test
    public void ifSetDifficultLevelThenBoardHaveLockedCells() {
        Board testBoard = new Board(10);
        testBoard.init(5);
        new Thread(() -> {
            boolean isLocked = false;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (testBoard.getLock(new Cell(i, j)).isLocked()) {
                        isLocked = true;
                        break;
                    }
                }
            }
            assertThat(isLocked, is(true));
        }).start();
    }


    @Test
    public void ifCreateNewBoardWithSize10ThenGetSizeShouldBeReturn10() {
        Board testBoard = new Board(10);
        assertThat(testBoard.getSize(), is(10));
    }
}