package ru.job4j.monitore;

import java.util.Objects;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 28.04.2018
 */
public class User {

    private final int id;
    private int amount;

    User(final int id, final int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return this.id;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && amount == user.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
