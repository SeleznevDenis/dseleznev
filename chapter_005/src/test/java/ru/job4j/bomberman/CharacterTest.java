package ru.job4j.bomberman;

import org.junit.Test;
import ru.job4j.wait.SimpleBlockingQueue;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test Character.
 * @author Denis Seleznev
 * @version $Id$
 * @since 13.05.2018
 */
public class CharacterTest {

    @Test
    public void ifMoveTheCharacterItWillMove() {
        Board testBoard = new Board(10);
        testBoard.init(0);
        SimpleBlockingQueue<Cell> moves = new SimpleBlockingQueue<>();
        moves.offer(new Cell(1, 1));
        moves.offer(new Cell(1, 1));
        Character character = new Character(testBoard, new Cell(1, 1), moves);
        SimpleBlockingQueue<Cell> output = character.getOutput();
        Thread first = new Thread(character);
        first.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        first.interrupt();
        try {
            first.join();
        } catch (InterruptedException e) {
            first.interrupt();
        }
        ArrayList<Cell> result = new ArrayList<>();
        result.add(output.poll());
        result.add(output.poll());
        result.add(output.poll());
        assertThat(result, is(new ArrayList<>(asList(new Cell(2, 2), new Cell(3, 3), new Cell(3, 3)))));
    }
}