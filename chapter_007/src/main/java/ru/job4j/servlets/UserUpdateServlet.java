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
            req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
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
                req.getParameter("email"),
                req.getParameter("role"),
                req.getParameter("password")
        );
        updatedUser.setId(Integer.parseInt(req.getParameter("id")));
        String message = "";
        String error = "";
        if (this.validator.update(updatedUser)) {
            message = "User successfully updated";
        } else {
            error = "User not updated";
        }
        req.setAttribute("message", message);
        req.setAttribute("error", error);
        try {
            req.setAttribute("users", this.validator.findAll());
            req.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
           LOG.error(e.getMessage(), e);
        }
    }
}
