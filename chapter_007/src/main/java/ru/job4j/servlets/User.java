package ru.job4j.servlets;

import java.util.Calendar;
import java.util.Objects;

/**
 * Data model User.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class User {
    /**
     * Идентификатор пользователя.
     */
    private int id;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Логин пользователя.
     */
    private String login;

    private String password;



    private String country;
    private String city;

    private Role role;

    /**
     * Адрес электронной почты пользователя.
     */
    private String email;

    /**
     * Дата создания пользователя.
     */
    private Calendar createDate;

    /**
     * Инициализирует одноименные поля User:
     * @param name имя.
     * @param login логин.
     * @param email адрес электронной почты.
     */
    public User(String name, String login, String email, String role, String password) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.setRole(role);
        this.password = password;
    }

    /**
     * Инициализирует пользователя на основе данных из объекта Message.
     * @param msg Объект UserServlet.Message.
     */
    public User(UserServlet.Message msg) {
        this.id = Integer.parseInt(msg.getId());
        this.name = msg.getName();
        this.login = msg.getLogin();
        this.email = msg.getEmail();
        this.createDate = msg.getCreateDate();
    }

    public User(final int id) {
        this.id = id;
    }

    public User() {
    }



    enum Role {
        ADMINISTRATOR,
        USER
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
        return id == user.id
                && Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && Objects.equals(createDate, user.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, createDate);
    }

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role.toString();
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRole(String role) {
        if (role != null) {
            this.role = Enum.valueOf(Role.class, role.toUpperCase());
        }
    }
}
