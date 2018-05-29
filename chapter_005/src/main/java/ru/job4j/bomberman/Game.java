package ru.job4j.bomberman;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.List;

/**
 * Описывает игру BomberMan.
 * @author Denis Seleznev
 * @version $Id$
 * @since 13.05.2018
 */
public class Game {
    /**
     * Хранит ссылку на игровое поле.
     */
    private final Board board;

    /**
     * Количество персонажей со случайным вводом команд.
     */
    private int randomInputCharacters;

    /**
     * Содержит ссылки на все потоки задействуемые в Game.
     */
    private Thread[] allThreads;

    /**
     * Указатель для записи в allThreads.
     */
    private int threadPosition;

    /**
     * Список пользовательских очередей ввода,
     * на основе него создаются персонажи управляемые игроками.
     */
    private List<SimpleBlockingQueue<Cell>> inputsPlayers;

    /**
     * Инициализирует размер игрового поля и количество персонажей со случайным вводом команд.
     * @param boardSize размер игрового поля.
     * @param randomInputCharacters количество персонажей со случайным вводом команд.
     */
    Game(int boardSize, int randomInputCharacters, List<SimpleBlockingQueue<Cell>> inputsPlayers) {
        this.board = new Board(boardSize);
        this.randomInputCharacters = randomInputCharacters;
        this.allThreads = new Thread[randomInputCharacters * 2 + inputsPlayers.size()];
        this.inputsPlayers = inputsPlayers;
    }

    /**
     * Запускает игру: инициализирует игровое поле, запускает потоки случайного ввода команд и
     * потоки персонажей. Записывает все потоки в allThreads.
     * @param difficultLevel уровень сложности игрового поля.
     */
    public void startGame(int difficultLevel) {
        this.board.init(difficultLevel);
        int rowPosition = 0;
        int columnPosition = 0;
        int userNumbers = 0;
        for (int i = 0; i < this.randomInputCharacters + this.inputsPlayers.size(); i++) {
            if (columnPosition >= this.board.getSize()) {
                rowPosition++;
                columnPosition = 0;
            }
            if (i < this.randomInputCharacters) {
                SimpleBlockingQueue<Cell> input = new SimpleBlockingQueue<>();
                this.allThreads[threadPosition] = new Thread(
                        new Character(this.board, new Cell(rowPosition, columnPosition++), input)
                );
                this.allThreads[threadPosition++].start();
                this.allThreads[threadPosition] = new Thread(new RandomInput(input, 1000));
                this.allThreads[threadPosition++].start();
            } else {
                this.allThreads[threadPosition] = new Thread(
                        new Character(this.board, new Cell(
                                rowPosition, columnPosition++), this.inputsPlayers.get(userNumbers++)
                        )
                );
                this.allThreads[threadPosition++].start();
            }
        }
    }

    /**
     * Завершает игру: дает команду остановки всем запущенным game потокам и дожидается их остановки.
     */
    public void stopGame() {
        for (Thread thread : this.allThreads) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
