package ru.job4j.chess;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class KnightTest {
    /**
     * Test way при движении коня по x - 1 и y - 2.
     */
    @Test
    public void ifKnightGoingFromPointFourFourToPointThreeTwo() throws ImpossibleMoveException {
        Knight testKnight = new Knight(new Cell(4, 4));
        Cell[] result = testKnight.way(new Cell(4, 4), new Cell(3, 2));
        assertThat(result, is(new Cell[]{new Cell(3, 2)}));
    }

    /**
     * Test way при движении коня по x - 2 и y - 1.
     */
    @Test
    public void ifKnightGoingFromPointFourFourToPointTwoThree() throws ImpossibleMoveException {
        Knight testKnight = new Knight(new Cell(4, 4));
        Cell[] result = testKnight.way(new Cell(4, 4), new Cell(2, 3));
        assertThat(result, is(new Cell[]{new Cell(2, 3)}));
    }

    /**
     * Test way при движении коня по x - 2 и y + 1.
     */
    @Test
    public void ifKnightGoingFromPointFourFourToPointTwoFive() throws ImpossibleMoveException {
        Knight testKnight = new Knight(new Cell(4, 4));
        Cell[] result = testKnight.way(new Cell(4, 4), new Cell(2, 5));
        assertThat(result, is(new Cell[]{new Cell(2, 5)}));
    }

    /**
     * Test way при движении коня по x - 1 и y + 2.
     */
    @Test
    public void ifKnightGoingFromPointFourFourToPointThreeSix() throws ImpossibleMoveException {
        Knight testKnight = new Knight(new Cell(4, 4));
        Cell[] result = testKnight.way(new Cell(4, 4), new Cell(3, 6));
        assertThat(result, is(new Cell[]{new Cell(3, 6)}));
    }

    /**
     * Test way при конечных координатах, неотвечающих правилу передвижения коня.
     */
    @Test
    public void ifWrongWayThenReturnImeException() {
        Knight testKnight = new Knight(new Cell(4, 4));
        try {
            testKnight.way(new Cell(4, 4), new Cell(5, 5));
        } catch (ImpossibleMoveException ime) {
            assertThat("Конь так не ходит", is(ime.getMessage()));
        }
    }
}


