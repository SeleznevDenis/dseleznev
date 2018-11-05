package ru.job4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Memory Storage.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class MemoryStorage implements Storage {

    /**
     * Добавляет объект в хранилище.
     * @param user объект для добавления.
     */
    @Override
    public void add(User user) {
        System.out.println("user was added");
    }
}
