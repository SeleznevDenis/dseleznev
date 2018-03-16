package ru.job4j.chess;

/**
 * Объект класса является исключением (Невозможный ход) расширяет Exception.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ImpossibleMoveException extends Exception {
    /**
     * Конструктор, инициализирует сообщение об ошибке.
     * @param msg сообщение.
     */
    public ImpossibleMoveException(String msg) {
        super(msg);
    }
}
