package ru.job4j.condition;

/**
 * Triangle
 * Объект класса Triangle содержит координаты 3х вершин треугольника
 * на плоскости и метод для нахождения площади треугольника.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Triangle {
    private Point a;
    private Point b;
    private Point c;

    /**
     * Triangle
     * Метод задает координаты вершин треугольника
     * @param a - координаты точки а.
     * @param b - координаты точки b.
     * @param c - координаты точки c.
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * period
     * @param ab - расстояние между точками ab.
     * @param ac - расстояние между точками ac.
     * @param bc - расстояние между точками bc.
     * @return половина периме
     */
    public double period(double ab, double ac, double bc) {
        return (ab + ac + bc) / 2;
    }

    /**
     * area
     * @return rsl площадь треугольника.
     */
    public double area() {
        double rsl = -1;
        double ab = this.a.distanceTo(this.b);
        double ac = this.a.distanceTo(this.c);
        double bc = this.b.distanceTo(this.c);
        double p = this.period(ab, ac, bc);
        if (this.exist(ab, ac, bc)) {
            rsl = Math.sqrt(p * (p - ab) * (p - ac) * (p - bc));
        }
        return rsl;
    }

    /**
     * exist определяет исключающие построение треугольника случаи.
     * @param ab длина отрезка ab.
     * @param ac длина отрезка ac.
     * @param bc длина отрезка bc.
     * @return boolean.
     */
    private boolean exist(double ab, double ac, double bc) {
        if (ab >= ac && ab >= bc) {
            return ab < ac + bc;
        } else {
            if (ac >= ab && ac >= bc) {
              return ac < ab + bc;
            } else {
                return bc < ab + ac;
            }
        }
    }
}
