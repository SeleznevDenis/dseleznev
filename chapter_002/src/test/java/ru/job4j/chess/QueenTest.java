package ru.job4j.chess;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class QueenTest {
    /**
     * Test way при движении ферзя по x++.
     */
    @Test
    public void ifQueenGoingFromPointFourFourToPointSixFour() throws ImpossibleMoveException {
        Queen testQueen = new Queen(new Cell(4, 4));
        Cell[] result = testQueen.way(new Cell(4, 4), new Cell(6, 4));
        assertThat(result, is(new Cell[]{new Cell(5, 4), new Cell(6, 4)}));
    }

    /**
     * Test way при движении ферзя по x++ и y++.
     */
    @Test
    public void ifQueenGoingFromPointFourFourToPointSixSix() throws ImpossibleMoveException {
        Queen testQueen = new Queen(new Cell(4, 4));
        Cell[] result = testQueen.way(new Cell(4, 4), new Cell(6, 6));
        assertThat(result, is(new Cell[]{new Cell(5, 5), new Cell(6, 6)}));
    }

    /**
     * Test way при движении ферзя по y++.
     */
    @Test
    public void ifQueenGoingFromPointFourFourToPointFourSix() throws ImpossibleMoveException {
        Queen testQueen = new Queen(new Cell(4, 4));
        Cell[] result = testQueen.way(new Cell(4, 4), new Cell(4, 6));
        assertThat(result, is(new Cell[]{new Cell(4, 5), new Cell(4, 6)}));
    }

    /**
     * Test way при движении ферзя по x--.
     */
    @Test
    public void ifQueenGoingFromPointFourFourToPointTwoFour() throws ImpossibleMoveException {
        Queen testQueen = new Queen(new Cell(4, 4));
        Cell[] result = testQueen.way(new Cell(4, 4), new Cell(2, 4));
        assertThat(result, is(new Cell[]{new Cell(3, 4), new Cell(2, 4)}));
    }

    /**
     * Test way при конечных координатах, неотвечающих правилу передвижения ферзя.
     */
    @Test
    public void ifWrongWayThenReturnImeException() {
        Queen testQueen = new Queen(new Cell(4, 4));
        try {
            testQueen.way(new Cell(4, 4), new Cell(5, 6));
        } catch (ImpossibleMoveException ime) {
            assertThat("Ферзь так не ходит", is(ime.getMessage()));
        }
    }
}
