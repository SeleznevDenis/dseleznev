package ru.job4j.chess;

import java.util.Arrays;
/**
 * Объект класса имитирует фигуру - ладья.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Rook extends Figure {
    /**
     * Конструктор, инициализирует координаты ладьи.
     * @param position координаты.
     */
    public Rook(Cell position) {
        super(position);
    }

    /**
     * Метод проверяет возможность хода для ладьи и возвращает
     * пройденные им координаты в виде массива.
     * @param source начальные координаты ладьи
     * @param dest конечные координаты ладьи
     * @return Массив координат типа Cell которые прошела ладья,
     * на пути от начальных координат до конечных.
     */
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int differenceX = Math.abs(dest.getX() - source.getX());
        int differenceY = Math.abs(dest.getY() - source.getY());
        if (dest.getX() < 0 && dest.getY() < 0 && dest.getX() > 9 && dest.getY() > 9
                && (differenceX != 0 || differenceY != 0)) {
            throw new ImpossibleMoveException("Ладья так не ходит");
        }
        int moveForX = 0;
        if (differenceX != 0) {
            moveForX = source.getX() < dest.getX() ? 1 : -1;
        }
        int moveForY = 0;
        if (differenceY != 0) {
            moveForY = source.getY() < dest.getY() ? 1 : -1;
        }
        Cell[] way = new Cell[8];
        int wayPosition;
        int currentX = source.getX();
        int currentY = source.getY();
        int movesNumber = Math.max(differenceX, differenceY);
        for (wayPosition = 0; wayPosition < movesNumber; wayPosition++) {
            currentX += moveForX;
            currentY += moveForY;
            way[wayPosition] = new Cell(currentX, currentY);
        }
        return Arrays.copyOf(way, wayPosition);
    }

    /**
     * @param dest координаты, для нового объекта.
     * @return новый объект Rook c заданными координатами dest.
     */
    public Figure copy(Cell dest) {
        return new Rook(dest);
    }
}
