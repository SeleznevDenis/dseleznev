package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Авторизатор.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AuthServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private ValidateService validator = ValidateService.getInstance();

    /**
     * Отображает страницу auth.jsp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/WEB-INF/views/auth.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Авторизует пользователя в системе, если он есть в базе данных. Проверяет пароль.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        try {
            if (action.equals("signin")) {
                String login = req.getParameter("login");
                String password = req.getParameter("password");
                User foundUser = this.validator.findByLogin(login);
                if (foundUser != null && foundUser.getPassword().equals(password)) {
                    req.getSession().setAttribute("loginRole", new LoginRole(foundUser));
                    resp.sendRedirect(String.format("%s/list", req.getContextPath()));
                } else {
                    req.setAttribute("error", "Invalid login or password");
                    req.getRequestDispatcher("/WEB-INF/views/auth.jsp").forward(req, resp);
                }
            } else if (req.getParameter("action").equals("signout")) {
                req.getSession().invalidate();
                resp.sendRedirect(String.format("%s/signin", req.getContextPath()));
            }
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Объект, для установки параметров сессии,
     * Содержит в себе логин и роль пользователя.
     */
    public class LoginRole {
        String login;
        String role;

        public LoginRole(User user) {
            this.login = user.getLogin();
            this.role = user.getRole();
        }

        public String getLogin() {
            return login;
        }

        public String getRole() {
            return role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            LoginRole loginRole = (LoginRole) o;
            return Objects.equals(login, loginRole.login)
                    && Objects.equals(role, loginRole.role);
        }

        @Override
        public int hashCode() {
            return Objects.hash(login, role);
        }
    }
}
