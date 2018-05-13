package ru.job4j.bomberman;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Описывает персонажа.
 * @author Denis Seleznev
 * @version $Id$
 * @since 13.05.2018
 */
public class Character implements Runnable {
    /**
     * Хранит координаты текущего положения.
     */
    private volatile Cell currentCell;
    /**
     * Хранит ссылку на текущее игровое поле.
     */
    private final Board currentBoard;
    /**
     * Хранит ссылку на input. Представляет собой очередь ходов, которые должен совершить персонаж.
     */
    private SimpleBlockingQueue<Cell> moves;
    /**
     * Хранит ссылку на output. Совершенные изменения координат отправляются в эту очередь.
     */
    private SimpleBlockingQueue<Cell> output = new SimpleBlockingQueue<>();

    /**
     * Инициализирует текущую доску, текущее местоположение и input.
     * @param currentBoard текущее игровое поле.
     * @param currentCell текущее местоположение.
     * @param moves input.
     */
    Character(Board currentBoard, Cell currentCell, SimpleBlockingQueue<Cell> moves) {
        this.currentBoard = currentBoard;
        this.currentCell = currentCell;
        this.moves = moves;
    }

    /**
     * Производит сдвиг персонажа, в соответствии с командой из очереди moves.
     * Проверяет возможен ли такой ход, а также свободна ли ячейка, если проверка не проходит,
     * рекурсивно вызывает себя и получает следующую команду на сдвиг.
     * В случае правильного хода, и свободной ячейки - блокирует следующую ячейку и разблокирует предыдущую.
     */
    private void move() {
        Cell move = this.moves.poll();
        if (move != null) {
            final int newRow = this.currentCell.getRow() + move.getRow();
            final int newColumn = this.currentCell.getColumn() + move.getColumn();
            if (newRow > 0 && newColumn > 0 && newRow <= this.currentBoard.getSize()
                    && newColumn <= this.currentBoard.getSize()) {
                Cell newCell = new Cell(newRow, newColumn);
                ReentrantLock oldLock = this.currentBoard.getLock(this.currentCell);
                ReentrantLock newLock = this.currentBoard.getLock(newCell);
                try {
                    if (!newLock.tryLock(500, TimeUnit.MILLISECONDS)) {
                        move();
                    } else {
                        this.currentCell = newCell;
                        oldLock.unlock();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                move();
            }
        }
    }

    /**
     * @return очередь содержащая ходы персонажа.
     */
    public SimpleBlockingQueue<Cell> getOutput() {
        return this.output;
    }

    /**
     * При запуске, блокирует текущую ячейку и производит движение персонажа.
     * Затем работает в цикле по условию isInterrupted:
     * Записывает ход в очередь output затем ждёт одну секунду
     */
    @Override
    public void run() {
        if (!this.currentBoard.getLock(this.currentCell).tryLock()) {
            throw new IllegalArgumentException();
        }
        while (!Thread.currentThread().isInterrupted()) {
            move();
            this.output.offer(this.currentCell);
            System.out.println(String.format("%s: on position %s", Thread.currentThread().getName(),
                    this.currentCell.getRow() + " " + currentCell.getColumn()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
