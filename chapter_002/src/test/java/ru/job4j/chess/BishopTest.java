package ru.job4j.chess;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class BishopTest {
    /**
     * Test way при движении слона по x-- и y--.
     */
    @Test
    public void ifBishopGoingFromPointFourFourToPointTwoTwo() throws ImpossibleMoveException {
        Bishop bishop = new Bishop(new Cell(4, 4));
        Cell[] result = bishop.way(new Cell(4, 4), new Cell(2, 2));
        Cell[] expect = {new Cell(3, 3), new Cell(2, 2)};
        assertThat(result, is(expect));
    }

    /**
     * Test way при движении слона по x++ и y++.
     */
    @Test
    public void ifBishopGoingFromPointFourFourToPointSixSix() throws ImpossibleMoveException {
        Bishop bishop = new Bishop(new Cell(4, 4));
        Cell[] result = bishop.way(new Cell(4, 4), new Cell(6, 6));
        Cell[] expect = {new Cell(5, 5), new Cell(6, 6)};
        assertThat(result, is(expect));
    }

    /**
     * Test way при движении слона по x++ и y--.
     */
    @Test
    public void ifBishopGoingFromPointFourFourToPointSixTwo() throws ImpossibleMoveException {
        Bishop bishop = new Bishop(new Cell(4, 4));
        Cell[] result = bishop.way(new Cell(4, 4), new Cell(6, 2));
        Cell[] expect = {new Cell(5, 3), new Cell(6, 2)};
        assertThat(result, is(expect));
    }

    /**
     * Test way при движении слона по x-- и y++.
     */
    @Test
    public void ifBishopGoingFromPointFourFourToPointTwoSix() throws ImpossibleMoveException {
        Bishop bishop = new Bishop(new Cell(4, 4));
        Cell[] result = bishop.way(new Cell(4, 4), new Cell(2, 6));
        Cell[] expect = {new Cell(3, 5), new Cell(2, 6)};
        assertThat(result, is(expect));
    }

    /**
     * Test way при конечных координатах, неотвечающих правилу передвижения слона.
     */
    @Test
    public void ifWrongWayThenReturnImeException() {
        Bishop bishop = new Bishop(new Cell(4, 4));
        try {
            bishop.way(new Cell(4, 4), new Cell(5, 6));
        } catch (ImpossibleMoveException ime) {
            assertThat("Слон так не ходит", is(ime.getMessage()));
        }
    }
}
