package ru.job4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JDBC Storage.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@Component
public class JdbcStorage implements Storage {

    /**
     * Добавляет объект в хранилище.
     * @param user объект для добавления.
     */
    @Override
    public void add(User user) {
        System.out.println("jdbc add");

    }
}
