package ru.job4j.chess;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class RookTest {
    /**
     * Test way при движении ладьи по x++.
     */
    @Test
    public void ifRookGoingFromPointFourFourToPointSixFour() throws ImpossibleMoveException {
        Rook testRook = new Rook(new Cell(4, 4));
        Cell[] result = testRook.way(new Cell(4, 4), new Cell(6, 4));
        assertThat(result, is(new Cell[]{new Cell(5, 4), new Cell(6, 4)}));
    }

    /**
     * Test way при движении ладьи по y++.
     */
    @Test
    public void ifRookGoingFromPointFourFourToPointSixSix() throws ImpossibleMoveException {
        Rook testRook = new Rook(new Cell(4, 4));
        Cell[] result = testRook.way(new Cell(4, 4), new Cell(4, 6));
        assertThat(result, is(new Cell[]{new Cell(4, 5), new Cell(4, 6)}));
    }

    /**
     * Test way при движении ладьи по x--.
     */
    @Test
    public void ifRookGoingFromPointFourFourToPointFourSix() throws ImpossibleMoveException {
        Rook testRook = new Rook(new Cell(4, 4));
        Cell[] result = testRook.way(new Cell(4, 4), new Cell(2, 4));
        assertThat(result, is(new Cell[]{new Cell(3, 4), new Cell(2, 4)}));
    }

    /**
     * Test way при конечных координатах, неотвечающих правилу передвижения ладьи.
     */
    @Test
    public void ifWrongWayThenReturnImeException() {
        Rook testRook = new Rook(new Cell(4, 4));
        try {
            testRook.way(new Cell(4, 4), new Cell(5, 5));
        } catch (ImpossibleMoveException ime) {
            assertThat("Ладья так не ходит", is(ime.getMessage()));
        }
    }
}
