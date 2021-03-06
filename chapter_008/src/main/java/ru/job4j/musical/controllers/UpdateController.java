package ru.job4j.musical.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.musical.dto.SessionDTO;
import ru.job4j.musical.entities.User;
import ru.job4j.musical.stores.UserStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Update Controller.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UpdateController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private static final UserStore USER_STORE = new UserStore();
    private static final ObjectMapper CONVERTER = new ObjectMapper();

    /**
     * Принимает данные пользователя и производит обновление пользователя в хранилище.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User updatedUser = CONVERTER.readValue(req.getReader().readLine(), User.class);
            USER_STORE.update(updatedUser.getId(), updatedUser);
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            HttpSession session = req.getSession();
            SessionDTO loginRole = new SessionDTO();
            loginRole.setLogin((String) session.getAttribute("login"));
            loginRole.setRole((String) session.getAttribute("role"));
            CONVERTER.writeValue(writer, loginRole);
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
