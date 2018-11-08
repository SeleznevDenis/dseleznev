package ru.job4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * User Storage.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */

public class UserStorage {

    private final Storage storage;

    public UserStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Добавляет user в хранилище
     * @param user объект для добавления.
     */
    public int add(User user) {
        return this.storage.add(user);
    }

    /**
     * Ищет юзера в хранилище.
     * @param id идентификатор искомого юзера.
     * @return искомый юзер.
     */
    public User findById(int id) {
        return this.storage.findById(id);
    }
}
