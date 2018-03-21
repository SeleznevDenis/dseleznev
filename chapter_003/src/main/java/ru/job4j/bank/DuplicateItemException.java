package ru.job4j.bank;

/**
 * Исключение, которое возникает в случае дублирующих элементов.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class DuplicateItemException extends Exception {
    /**
     * Конструктор, инициализирует сообщение об ошибке.
     * @param msg сообщение об ошибке.
     */
    public DuplicateItemException(String msg) {
        super(msg);
    }
}
