package ru.job4j.bank;

/**
 * Исключение, которое возникает в случае, если элемент не найден.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ItemNotFoundException extends Exception {
    /**
     * Конструктор, инициализирует сообщение об ошибке.
     * @param msg сообщение об ошибке.
     */
    public ItemNotFoundException(String msg) {
        super(msg);
    }
}
