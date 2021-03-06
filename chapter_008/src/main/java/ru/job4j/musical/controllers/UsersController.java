package ru.job4j.musical.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.musical.stores.UserStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Users controller.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UsersController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private static final UserStore USER_STORE = new UserStore();
    private static final ObjectMapper CONVERTER = new ObjectMapper();

    /**
     * Возвращает список пользователей на страницу.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        if (role.equals("user") || role.equals("moderator") || role.equals("admin")) {
            try {
                resp.setContentType("application/json");
                PrintWriter writer = resp.getWriter();
                CONVERTER.writeValue(writer, USER_STORE.findAll());
                writer.flush();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
