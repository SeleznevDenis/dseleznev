package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет, обновляющий данные пользователя.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserUpdateServlet extends HttpServlet {

    /**
     * Ссылка на объект ValidateService.
     */
    private final ValidateService validator = ValidateService.getInstance();

    private static final Logger LOG = LogManager.getLogger("Servlet");

    /**
     * Отображает на странице форму для редактирования пользователя с заполненными полями.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        int updatedUserId = Integer.parseInt(req.getParameter("id"));
        final User updatedUser = this.validator.findById(updatedUserId);
        if (updatedUser != null) {
            req.setAttribute("user", updatedUser);
        } else {
            req.setAttribute("message", "User not found");
        }
        try {
            req.getRequestDispatcher("/update.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Производит редактирование пользователя
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        User updatedUser = new User(
                req.getParameter("name"),
                req.getParameter("login"),
                req.getParameter("email")
        );
        updatedUser.setId(Integer.parseInt(req.getParameter("id")));
        String message = this.validator.update(updatedUser) ? "User successfully updated" : "User not found";
        req.setAttribute("message", message);
        try {
            req.getRequestDispatcher("/users.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
           LOG.error(e.getMessage(), e);
        }
    }
}