package ru.job4j.generic;

import java.util.Objects;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 31.03.2018
 */
public class Role extends Base {

    private String role;

    /**
     * Конструктор инициализирует поля id и role.
     * @param id
     * @param role
     */
    protected Role(String id, String role) {
        super(id);
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

}
