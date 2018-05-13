package ru.job4j.bomberman;

import java.util.Objects;

/**
 * Описывает координаты ячейки на 2х мерном поле.
 * @author Denis Seleznev
 * @version $Id$
 * @since 08.05.2018
 */
public class Cell {
    /**
     * Номер ряда.
     */
    private int row;
    /**
     * Номер столбца.
     */
    private int column;

    /**
     * Инициализирует номер ряда и столбца.
     * @param row номер ряда.
     * @param column номер столбца.
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return row == cell.row && column == cell.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
