package ru.job4j.servlets;

/**
 * Модель данных для JSON конвертера.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserDTO {
    private String firstName;
    private String lastName;
    private String description;
    private String sex;

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName, String sex, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}