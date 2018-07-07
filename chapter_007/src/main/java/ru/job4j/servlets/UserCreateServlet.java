package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет добавляющий пользователя в систему.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserCreateServlet extends HttpServlet {

    /**
     * Ссылка на объект ValidateService.
     */
    private final ValidateService validator = ValidateService.getInstance();

    private static final Logger LOG = LogManager.getLogger("Servlet");

    /**
     * Отображает на странице форму для создания пользователя.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp").forward(req, resp);
        } catch (IOException | ServletException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Выполняет добавление пользователя в систему.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String role = req.getParameter("role");
        String password = req.getParameter("password");
        String message = "";
        String error = "";
        if (this.validator.add(new User(name, login, email, role, password))) {
            message = "User was added";
        } else {
            error = "Incorrect input data, please try again.";
        }
        req.setAttribute("message", message);
        req.setAttribute("error", error);
        try {
            req.getRequestDispatcher("/WEB-INF/views/create_user.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
