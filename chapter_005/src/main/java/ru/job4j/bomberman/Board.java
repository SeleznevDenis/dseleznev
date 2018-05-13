package ru.job4j.bomberman;

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
     */
    public void init() {
        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board[row].length; column++) {
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
