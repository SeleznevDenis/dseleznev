package ru.job4j.musical.dto;

/**
 * Session Data transfer object.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class SessionDTO {
    private String login;
    private String role;
    private String message;
    private String password;

    public SessionDTO() {
    }

    public SessionDTO(String login, String role) {
        this.login = login;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
