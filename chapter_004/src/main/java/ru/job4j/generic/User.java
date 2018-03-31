package ru.job4j.generic;

import java.util.Objects;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 31.03.2018
 */
public class User extends Base {

    private String name;

    /**
     * Конструктор, инициализирует поля id и name.
     * @param id
     * @param name
     */
    protected User(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
