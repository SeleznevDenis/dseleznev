package ru.job4j.chess;

/**
 * Объект класса имитирует фигуру - конь.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Knight extends Figure {
    /**
     * Конструктор, инициализирует координаты коня.
     * @param position координаты.
     */
    public Knight(Cell position) {
        super(position);
    }

    /**
     * Метод проверяет возможность хода для коня и возвращает
     * пройденные им координаты в виде массива.
     * @param source начальные координаты коня.
     * @param dest конечные координаты коня.
     * @return Массив координат типа Cell которые прошел конь,
     * на пути от начальных координат до конечных.
     */
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int differenceX = Math.abs(dest.getX() - source.getX());
        int differenceY = Math.abs(dest.getY() - source.getY());
        if (dest.getX() < 0 && dest.getY() < 0 && dest.getX() > 9 && dest.getY() > 9
                && ((differenceX != 2 && differenceY != 1) || (differenceX != 1 && differenceY != 2))) {
            throw new ImpossibleMoveException("Конь так не ходит");
        }
        return new Cell[]{dest};
    }

    /**
     * @param dest координаты, для нового объекта.
     * @return новый объект Knight c заданными координатами dest.
     */
    public Figure copy(Cell dest) {
        return new Knight(dest);
    }
}
