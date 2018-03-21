package ru.job4j.bank;

/**
 * Исключение, возникает в случае недостатка денежных средств, для перевода.
 * @author Denis Seleznev (d.Selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class NotEnoughMoneyException extends Exception {
    /**
     * Конструктор, инициализирует сообщение об ошибке.
     * @param msg сообщение об ошибке.
     */
    public NotEnoughMoneyException(String msg) {
        super(msg);
    }

}
