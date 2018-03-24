package ru.job4j.chess;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class PawnTest {
    /**
     * Test way при движении пешки по x + 2.
     */
    @Test
    public void ifPawnGoingFromPointTwoTwoToPointFourTwo() throws ImpossibleMoveException {
        Pawn testPawn = new Pawn(new Cell(2, 2));
        Cell[] result = testPawn.way(new Cell(2, 2), new Cell(4, 2));
        assertThat(result, is(new Cell[]{new Cell(3, 2), new Cell(4, 2)}));
    }

    /**
     * Test way при движении пешки по x + 1.
     */
    @Test
    public void ifPawnGoingFromPointThreeTwoToPointFourTwo() throws ImpossibleMoveException {
        Pawn testPawn = new Pawn(new Cell(3, 2));
        Cell[] result = testPawn.way(new Cell(3, 2), new Cell(4, 2));
        assertThat(result, is(new Cell[]{new Cell(4, 2)}));
    }

    /**
     * Test way при конечных координатах, неотвечающих правилу передвижения пешки.
     */
    @Test
    public void ifWrongWayThenReturnImeException() {
        Pawn testPawn = new Pawn(new Cell(4, 4));
        try {
            testPawn.way(new Cell(4, 4), new Cell(6, 4));
        } catch (ImpossibleMoveException ime) {
            assertThat("Пешка так не ходит", is(ime.getMessage()));
        }
    }
}
