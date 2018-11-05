package ru.job4j;

/**
 * Storage interface.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public interface Storage {

    /**
     * Метод для добавления объекта в хранилище.
     * @param user объект для добавления.
     */
    void add(User user);
}
