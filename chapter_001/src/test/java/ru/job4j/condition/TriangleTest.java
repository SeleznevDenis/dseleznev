package ru.job4j.condition;

import org.junit.Test;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;


/**
 * TriangleTest.
 * Тестирует объект класса triangle.
 */
public class TriangleTest {
    /**
     * whenAreaSetThreePointsThenTreangleArea.
     * Тестирует методы для нахождения площади треугольника
     */
    @Test
    public void whenAreaSetThreePointsThenTreangleArea() {
        //создаем три объекта класса Point.
        Point a = new Point(0, 0);
        Point b = new Point(0, 2);
        Point c = new Point(2, 0);
        // создаем объект треугольник и передаем в него объекты точек.
        Triangle triangle = new Triangle(a, b, c);
        // Вычисляем площадь.
        double result = triangle.area();
        // Задаем ожидаемый результат.
        double expected = 2D;
        // Проверяем результат и ожидаемое значение.
        assertThat(result, closeTo(expected, 0.1));
    }

    /**
     * whenExistFalse.
     * Тестируем метод для нахождения площади треугольника
     * в случае если треугольник построить невозможно.
     */
    @Test
    public void whenExistFalse() {
        Point a = new Point(0, 0);
        Point b = new Point(0, 0);
        Point c = new Point(0, 1);
        Triangle triangle = new Triangle(a, b, c);
        double result = triangle.area();
        double expected = -1D;
        assertThat(result, is(expected));

    }


}
