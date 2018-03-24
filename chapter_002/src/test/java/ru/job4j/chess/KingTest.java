package ru.job4j.chess;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class KingTest {
    /**
     * Test way при движении Короля по x-- и y--.
     */
    @Test
    public void ifKingGoingFromPointFourFourToPointThreeThree() throws ImpossibleMoveException {
        King testKing = new King(new Cell(4, 4));
        Cell[] result = testKing.way(new Cell(4, 4), new Cell(3, 3));
        Cell[] expect = {new Cell(3, 3)};
        assertThat(result, is(expect));
    }

    /**
     * Test way при движении короля по x++ и y++.
     */
    @Test
    public void ifKingGoingFromPointFourFourToPointFiveFive() throws ImpossibleMoveException {
        Bishop bishop = new Bishop(new Cell(4, 4));
        Cell[] result = bishop.way(new Cell(4, 4), new Cell(5, 5));
        Cell[] expect = {new Cell(5, 5)};
        assertThat(result, is(expect));
    }

    /**
     * Test way при движении короля по x++ и y--.
     */
    @Test
    public void ifKingGoingFromPointFourFourToPointFiveThree() throws ImpossibleMoveException {
        Bishop bishop = new Bishop(new Cell(4, 4));
        Cell[] result = bishop.way(new Cell(4, 4), new Cell(5, 3));
        Cell[] expect = {new Cell(5, 3)};
        assertThat(result, is(expect));
    }

    /**
     * Test way при движении короля по x-- и y++.
     */
    @Test
    public void ifKingGoingFromPointFourFourToPointThreeFive() throws ImpossibleMoveException {
        Bishop bishop = new Bishop(new Cell(4, 4));
        Cell[] result = bishop.way(new Cell(4, 4), new Cell(3, 5));
        Cell[] expect = {new Cell(3, 5)};
        assertThat(result, is(expect));
    }

    /**
     * Test way при конечных координатах, неотвечающих правилу передвижения короля.
     */
    @Test
    public void ifWrongWayThenReturnImeException() {
        Bishop bishop = new Bishop(new Cell(4, 4));
        try {
            bishop.way(new Cell(4, 4), new Cell(5, 7));
        } catch (ImpossibleMoveException ime) {
            assertThat("Король так не ходит", is(ime.getMessage()));
        }
    }
}


