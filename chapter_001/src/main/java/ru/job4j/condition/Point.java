package ru.job4j.condition;

/**
 * Point.
 * Объект класса поинт имеет координаты (x , y) и рассчитывает
 * расстояние до другого объекта этого же класса.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Point {

    private int x;
    private int y;

    /**
     * Point
     * Задает координаты объекту.
     * @param x
     * @param y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * distanceTo
     * Метод использует координаты текущего объекта и объекта заданного
     * параметром that, высчитывает и возвращает расстояние между ними на плоскости.
     * @param that
     * @return
     */
    public double distanceTo(Point that) {
        return Math.sqrt(Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2));
    }

    /**
     * main
     * Создает объекты a и b, задает их координаты и
     * выводит величину расстояния между ними в консоль.
     * @param args
     */
    public static void main(String[] args) {
        Point a = new Point(0, 1);
        Point b = new Point(2, 5);
        double result = a.distanceTo(b);
        System.out.println("Расстояние между двумя точками A и B : " + result);
    }
}

