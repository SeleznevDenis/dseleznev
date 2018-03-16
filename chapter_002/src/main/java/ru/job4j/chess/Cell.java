package ru.job4j.chess;

/**
 * Объект класса имитирует координаты ячейки на плоскости.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Cell {

    private int x;
    private int y;

    /**
     * Конструктор инициализирующий поля x и y.
     * @param x значение для поля x.
     * @param y значение для поля y.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return координату по оси x.
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return координату по оси y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Переопределенный метод equals класса object,
     * для сравнения координат Cell по полям x и y.
     * @param obj объект сравнения.
     * @return boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            Cell cellTest = (Cell) obj;
            return (this.x == cellTest.x && this.y == cellTest.y);
        }
    }

    /**
     * Переопределенный метод hashCode класса Object,
     * Для генерации hashCode в зависимости от полей x, y.
     * @return hashCode.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getX();
        result = prime * result + this.getY();
        return result;
    }
}
