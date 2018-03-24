package ru.job4j.chess;

/**
 * Объект класса имитирует фигуру - король.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class King extends Figure {

    /**
     * Конструктор, инициализирует координаты короля.
     * @param position координаты.
     */
    public King(Cell position) {
        super(position);
    }

    /**
     * Метод проверяет возможность хода для короля и возвращает
     * пройденные им координаты в виде массива.
     * @param source начальные координаты короля.
     * @param dest конечные координаты короля.
     * @return Массив координат типа Cell которые прошел король,
     * на пути от начальных координат до конечных.
     */
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        if (dest.getX() < 0 && dest.getY() < 0 && dest.getX() > 9 && dest.getY() > 9
                && (Math.abs(dest.getX() - source.getX()) != 1 || Math.abs(dest.getY() - source.getY()) != 1)) {
            throw new ImpossibleMoveException("Король так не ходит");
        }
        return new Cell[]{dest};
    }

    /**
     * @param dest координаты, для нового объекта.
     * @return новый объект King c заданными координатами dest.
     */
    public Figure copy(Cell dest) {
        return new King(dest);
    }
}
