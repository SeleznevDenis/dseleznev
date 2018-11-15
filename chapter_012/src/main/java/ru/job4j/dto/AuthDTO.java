package ru.job4j.dto;

/**
 * Описывает объект передающий данные авторизации.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AuthDTO {

    private String login;
    private String password;
    private String role;
    private String message;

    public AuthDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public AuthDTO(String message) {
        this.message = message;
    }

    public AuthDTO() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
