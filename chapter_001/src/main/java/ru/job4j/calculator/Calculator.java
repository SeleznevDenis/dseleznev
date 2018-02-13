package ru.job4j.calculator;

/**
 * Calculator.
 * Объект класса Calculator содержит переменную result
 * и методы для выполнения сложения, вычитания, деления и умножения.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Calculator {

    private double result;
    /**
     * add
     * @param first
     * @param second
     * Производит операцию сложения параметров
     * и записывает результат в this.result.
     */
    void add(double first, double second) {
        this.result = first + second;
    }
    /**
     * subtract
     * @param first
     * @param second
     * Производит операцию вычитания параметра second
     * из параметра first и записывает результат в
     * this.result.
     */
    void subtract(double first, double second) {
        this.result = first - second;
    }
    /**
     * div
     * @param first
     * @param second
     * производит операцию деления параметра first
     * на параметр second и записывает результат в
     * this.result.
     */
    void div(double first, double second) {
        this.result = first / second;
    }
    /**
     * multiple
     * @param first
     * @param second
     * Производит перемножение параметров и записывает
     * результат в this.result.
     */
    void multiple(double first, double second) {
        this.result = first * second;
    }
    /**
     * getResult
     * @return
     * Возвращает значение переменной this.result.
     */
    double getResult() {
    return this.result;
    }
}
