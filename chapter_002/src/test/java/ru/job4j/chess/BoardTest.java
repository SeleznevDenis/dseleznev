package ru.job4j.chess;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class BoardTest {
    /**
     * Test add.
     */
    @Test
    public void ifAddNewBishopWithCellFourFourToBoardThenFiguresHaveBishop() {
        Bishop testBishop = new Bishop(new Cell(4, 4));
        Board testBoard = new Board();
        testBoard.add(testBishop);
        Cell result = testBoard.getFigures()[0].position;
        assertThat(result, is(new Cell(4, 4)));
    }

    /**
     * Test checkWay.
     */
    @Test
    public void ifAddBishopCheckWayFromFourFourToTwoTwo()
            throws FigureNotFoundException, ImpossibleMoveException {
        Board testBoard = new Board();
        testBoard.add(new Bishop(new Cell(4, 4)));
        Cell[] result = testBoard.checkWay(new Cell(4, 4), new Cell(2, 2));
        Cell[] expect = {new Cell(3, 3), new Cell(2, 2)};
        assertThat(result, is(expect));
    }

    /**
     * Test FigureNotFoundException in checkWay.
     */
    @Test
    public void checkWayIfFigureNotFoundInFiguresThenThrowException() throws ImpossibleMoveException {
        Board testBoard = new Board();
        try {
            testBoard.checkWay(new Cell(4, 4), new Cell(2, 2));
        } catch (FigureNotFoundException fnfe) {
            assertThat(fnfe.getMessage(), is("В ячейке: 4 | 4 нет фигуры."));
        }
    }

    /**
     * Test checkFigureWay if way is busy.
     */
    @Test
    public void ifFigureMustPassBusyWayThenCheckFigureWayReturnFalse() {
        Board testBoard = new Board();
        testBoard.add(new Bishop(new Cell(4, 4)));
        boolean result = testBoard.checkFigureWay(new Cell[]{new Cell(3, 3), new Cell(4, 4)});
        assertThat(result, is(false));
    }

    /**
     * Test checkFigureWay if way is free.
     */
    @Test
    public void ifFigureMustPassFreeWayThenCheckFigureWayReturnTrue() {
        Board testBoard = new Board();
        testBoard.add(new Bishop(new Cell(2, 2)));
        boolean result = testBoard.checkFigureWay(new Cell[]{new Cell(3, 3), new Cell(4, 4)});
        assertThat(result, is(true));
    }

    /**
     * Test move.
     */
    @Test
    public void ifMoveFigureFromSourceToDestOnFreeWayThenFigureCellIsDest()
            throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Board testBoard = new Board();
        testBoard.add(new Bishop(new Cell(2, 2)));
        testBoard.move(new Cell(2, 2), new Cell(4, 4));
        assertThat(testBoard.getFigures()[0].position, is(new Cell(4, 4)));
    }

    /**
     * Test move if way busy.
     */
    @Test
    public void ifMoveFigureFromSourceToDestOnBusyWayThenThrowOccupiedWayException()
            throws  ImpossibleMoveException, FigureNotFoundException {
        Board testBoard = new Board();
        testBoard.add(new Bishop(new Cell(2, 2)));
        testBoard.add(new Bishop(new Cell(3, 3)));
        try {
            testBoard.move(new Cell(2, 2), new Cell(4, 4));
        } catch (OccupiedWayException owe) {
            assertThat(owe.getMessage(), is("Ячейка: 3 | 3, занята фигурой: Bishop"));
        }
    }
}
