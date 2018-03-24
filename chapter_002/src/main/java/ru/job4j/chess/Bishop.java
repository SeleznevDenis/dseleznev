package ru.job4j.chess;

import java.util.Arrays;

/**
 * Объект класса имитирует фигуру слон.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Bishop extends Figure {

    /**
     * Конструктор, инициализирует координаты слона.
     * @param position координаты.
     */
    public Bishop(Cell position) {
        super(position);
    }

    /**
     * Метод проверяет возможность хода для слона и возвращает
     * пройденные им координаты в виде массива.
     * @param source начальные координаты слона
     * @param dest конечные координаты слона
     * @return Массив координат типа Cell которые прошел слон,
     * на пути от начальных координат до конечных.
     */
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        if (dest.getX() < 0 && dest.getY() < 0 && dest.getX() > 9 && dest.getY() > 9
                && Math.abs(dest.getX() - source.getX()) != Math.abs(dest.getY() - source.getY())) {
            throw new ImpossibleMoveException("Слон так не ходит");
        }
        Cell[] way = new Cell[8];
        int wayPosition;
        int currentX = source.getX();
        int currentY = source.getY();
        int movesNumber = Math.abs(dest.getX() - source.getX());
        int moveForX = source.getX() < dest.getX() ? 1 : -1;
        int moveForY = source.getY() < dest.getY() ? 1 : -1;
        for (wayPosition = 0; wayPosition < movesNumber; wayPosition++) {
            currentX += moveForX;
            currentY += moveForY;
            way[wayPosition] = new Cell(currentX, currentY);
        }
        return Arrays.copyOf(way, wayPosition);
    }

    /**
     * @param dest координаты, для нового объекта.
     * @return новый объект Bishop c заданными координатами dest.
     */
    public Figure copy(Cell dest) {
        return new Bishop(dest);
    }
}
