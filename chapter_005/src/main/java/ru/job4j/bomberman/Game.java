package ru.job4j.bomberman;

import ru.job4j.wait.SimpleBlockingQueue;

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
     * Инициализирует размер игрового поля и количество персонажей со случайным вводом команд.
     * @param boardSize размер игрового поля.
     * @param randomInputCharacters количество персонажей со случайным вводом команд.
     */
    Game(int boardSize, int randomInputCharacters) {
        this.board = new Board(boardSize);
        this.randomInputCharacters = randomInputCharacters;
        this.allThreads = new Thread[randomInputCharacters * 2];
    }

    /**
     * Запускает игру: инициализирует игровое поле, запускает потоки случайного ввода команд и
     * и потоки персонажей. Записывает все потоки в allThreads.
     */
    public void startGame() {
        this.board.init();
        int rowPosition = 0;
        int columnPosition = 0;
        for (int i = 0; i < this.randomInputCharacters; i++) {
            if (columnPosition >= this.board.getSize()) {
                rowPosition++;
                columnPosition = 0;
            }
            SimpleBlockingQueue<Cell> input = new SimpleBlockingQueue<>();
            this.allThreads[threadPosition] = new Thread(
                    new Character(this.board, new Cell(rowPosition, columnPosition++), input)
            );
            this.allThreads[threadPosition++].start();
            this.allThreads[threadPosition] = new Thread(new RandomInput(input, 1000));
            this.allThreads[threadPosition++].start();
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
