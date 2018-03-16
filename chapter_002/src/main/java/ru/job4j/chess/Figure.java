package ru.job4j.chess;

/**
 * Абстрактный класс имитирующий фигуру на шахматной доске
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public abstract class Figure {

    final Cell position;

    public Figure() {
        this.position = null;
    }

    protected Figure(Cell position) {
        this.position = position;
    }

    protected abstract Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException;

    protected abstract Figure copy(Cell dest);
}
