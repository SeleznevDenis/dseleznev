package ru.job4j.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Отображает таблицу пользователей.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UsersServlet extends HttpServlet {
    /**
     * Хранит ссылку на объект ValidateService.
     */
    private final ValidateService validator = ValidateService.getInstance();

    private static final Logger LOG = LogManager.getLogger("Servlet");

    /**
     * Отображает страницу с таблицей пользователей и кнопками редактирования и удаления.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            req.setAttribute("users", this.validator.findAll());
            req.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Удаляет пользователя из системы.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String deletingUserId = req.getParameter("id");
        resp.setContentType("text/html");
        String message = "";
        if (deletingUserId != null) {
            if (this.validator.delete(Integer.parseInt(deletingUserId))) {
                message = "User was deleted";
            } else {
                message = "User not found";
            }
        }
        req.setAttribute("message", message);
        req.setAttribute("users", this.validator.findAll());
        try {
            req.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
