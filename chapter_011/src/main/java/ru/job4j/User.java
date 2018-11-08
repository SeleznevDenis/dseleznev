package ru.job4j;

/**
 * User model.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
