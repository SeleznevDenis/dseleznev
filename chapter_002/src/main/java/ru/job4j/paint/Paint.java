package ru.job4j.paint;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Paint {
    /**
     * Выводит в консоль рисунок фигуры в зависимости от параметра shape.
     * @param shape Принимает объекты класса Triangle (рисует треугольник)
     *              и Square (рисует квадрат) и выводит рисунок в псевдографике.
     */
    public void draw(Shape shape) {
        System.out.println(shape.draw());
    }
}
