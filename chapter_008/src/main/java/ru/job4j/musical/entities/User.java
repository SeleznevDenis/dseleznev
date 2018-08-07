package ru.job4j.musical.entities;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Сущность пользователь.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class User {

    private int id;
    private String name;
    private String password;
    private String login;
    private Calendar createDate;
    private Role role;
    private Address address;
    private List<MusicType> musicType;

    public User() {

    }

    public User(String name, String password, String login) {
        this.name = name;
        this.password = password;
        this.login = login;
        this.createDate = createDate;
    }

    public User(String name, String password, String login, List<MusicType> type, Address address, Role role) {
        this.name = name;
        this.password = password;
        this.login = login;
        this.musicType = type;
        this.address = address;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<MusicType> getMusicType() {
        return musicType;
    }

    public void setMusicType(List<MusicType> musicType) {
        this.musicType = musicType;
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
                && Objects.equals(password, user.password)
                && Objects.equals(login, user.login)
                && Objects.equals(createDate, user.createDate)
                && Objects.equals(role, user.role)
                && Objects.equals(address, user.address)
                && Objects.equals(musicType, user.musicType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, login, createDate, role, address, musicType);
    }
}
