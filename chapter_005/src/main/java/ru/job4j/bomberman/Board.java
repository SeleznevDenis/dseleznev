package ru.job4j.bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Игровое поле.
 * @author Denis Seleznev
 * @version $Id$
 * @since 08.05.2018
 */
public class Board {
    /**
     * Игровое поле.
     */
    private final ReentrantLock[][] board;

    /**
     * Инициализирует игровое поле заданным размером.
     * @param size заданный размер.
     */
    Board(int size) {
        this.board = new ReentrantLock[size][size];
    }

    /**
     * Заполняет поле, объектами типа ReentrantLock.
     * Блокирует случайные ячейки, количество которых зависит от уровня сложности.
     * @param difficultyLevel уровень сложности.
     */
    public void init(final int difficultyLevel) {
        if (difficultyLevel < 0 || difficultyLevel > 10) {
            throw new IllegalArgumentException();
        }
        List<Integer> difficult = new ArrayList<>();
        for (int i = 1; i < difficultyLevel; i++) {
            difficult.add(i);
        }
        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board[row].length; column++) {
                ReentrantLock lock = new ReentrantLock();
                if (difficult.contains((Integer) new Random().nextInt(11))) {
                    lock.lock();
                }
                this.board[row][column] = new ReentrantLock();
            }
        }
    }

    /**
     * Возвращает ссылку на ReentrantLock по заданным координатам.
     * @param cell заданная координата ячейки.
     * @return Объект типа ReentrantLock, ссылка на который хранится в ячейке.
     */
    public ReentrantLock getLock(Cell cell) {
        return this.board[cell.getRow()][cell.getColumn()];
    }

    /**
     * @return размер игрового поля.
     */
    public int getSize() {
        return this.board.length;
    }
}
