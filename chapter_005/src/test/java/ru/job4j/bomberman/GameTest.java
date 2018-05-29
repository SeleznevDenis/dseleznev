package ru.job4j.bomberman;

import org.junit.Test;
import ru.job4j.wait.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Пример запуска игры bomberMan.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 13.05.2018
 */
public class GameTest {

    /**
     * Пример.
     * Запускает игру с игровым полем размером 10 на 10 ячеек
     * и двумя персонажами, которые двигаются случайно.
     * Затем ждет 20 секунд и завершает игру.
     */
    @Test
    public void startGame() {
        List<SimpleBlockingQueue<Cell>> userInputs = new ArrayList<>();
        SimpleBlockingQueue<Cell> testUserInput = new SimpleBlockingQueue<>();
        userInputs.add(testUserInput);
        new Thread(new RandomInput(testUserInput, 200)).start();
        Game testGame = new Game(10, 2, userInputs);
        testGame.startGame(1);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testGame.stopGame();
    }
}