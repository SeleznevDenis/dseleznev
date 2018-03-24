package ru.job4j.chess;

/**
 * Объект класса имитирует фигуру - пешка.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Pawn extends Figure {

    /**
     * Конструктор, инициализирует координаты пешки.
     * @param position координаты.
     */
    public Pawn(Cell position) {
        super(position);
    }

    /**
     * Метод проверяет возможность хода для пешки и возвращает
     * пройденные им координаты в виде массива.
     * @param source начальные координаты пешки
     * @param dest конечные координаты пешки.
     * @return Массив координат типа Cell которые прошла пешка,
     * на пути от начальных координат до конечных.
     */
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int differenceX = Math.abs(dest.getX() - source.getX());
        boolean sourceXEqualTwo = source.getX() == 2;
        boolean doneDependingPosition = (sourceXEqualTwo ? differenceX == 2 || differenceX == 1 : differenceX == 1);
        if (dest.getX() < 0 && dest.getX() > 9 && dest.getY() != source.getY() && !doneDependingPosition) {
            throw new ImpossibleMoveException("Пешка так не ходит");
        }
        Cell[] result;
        if (sourceXEqualTwo && differenceX == 2) {
            result = new Cell[]{new Cell(source.getX() + 1, source.getY()), dest};
        } else {
            result = new Cell[]{dest};
        }
        return result;
    }

    /**
     * @param dest координаты, для нового объекта.
     * @return новый объект Pawn c заданными координатами dest.
     */
    public Figure copy(Cell dest) {
        return new Pawn(dest);
    }
}
