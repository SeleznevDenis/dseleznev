package ru.job4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User Storage.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@Component
public class UserStorage {

    private final Storage storage;

    @Autowired
    public UserStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Добавляет user в хранилище
     * @param user объект для добавления.
     */
    public void add(User user) {
        this.storage.add(user);
    }
}
