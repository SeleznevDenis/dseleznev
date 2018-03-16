package ru.job4j.chess;

/**
 * Объект класса является исключением (Путь занят) расширяет Exception.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class OccupiedWayException extends Exception {
    /**
     * Конструктор, инициализирует сообщение об ошибке.
     * @param msg сообщение об ошибке.
     */
    public OccupiedWayException(String msg) {
        super(msg);
    }
}
