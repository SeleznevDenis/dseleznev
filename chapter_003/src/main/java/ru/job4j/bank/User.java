package ru.job4j.bank;

import java.util.Objects;

/**
 * Карточка пользователя, с именем и паспортом.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class User {

    private String name;
    private String passport;

    /**
     * Конструктор, инициализирует имя пользователя и паспортные данные.
     * @param name имя.
     * @param passport паспортные данные
     */
    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    public String getName() {
        return this.name;
    }
    public String getPassport() {
        return this.passport;
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
        return Objects.equals(name, user.name)
                && Objects.equals(passport, user.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, passport);
    }
}
