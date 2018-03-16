package ru.job4j.chess;

/**
 * Объект класса является исключением (Фигура не найдена) наследует от Exception.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class FigureNotFoundException extends Exception {
    /**
     * Конструктор, инициализирует сообщение ошибки.
     * @param msg заданное сообщение.
     */
    public FigureNotFoundException(String msg) {
        super(msg);
    }
}
