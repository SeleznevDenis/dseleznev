package ru.job4j.tracker;

/**
 * Класс реализует исключение MenuOutException, наследует RuntimeException.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class MenuOutException extends RuntimeException {
    /**
     * Конструктор, инициализирует заданное сообщение об ошибке используя конструктор класса предка.
     * @param msg заданное сообщение об ошибке.
     */
    public MenuOutException(String msg) {
        super(msg);
    }
}
