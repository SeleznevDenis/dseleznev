package ru.job4j;

import java.util.List;

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
    int add(User user);

    /**
     * Ищет объект в хранилище.
     * @param id идентификатор искомого объекта.
     * @return искомый объект.
     */
    User findById(int id);

}
