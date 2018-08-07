package ru.job4j.musical.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.musical.dto.SessionDTO;
import ru.job4j.musical.stores.UserStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Deletion controller.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class DeletionController extends HttpServlet {

    /**
     * LOG4j логгер.
     */
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Хранилище пользователей.
     */
    private static final UserStore USER_STORE = new UserStore();

    /**
     * JSON конвертер
     */
    private static final ObjectMapper CONVERTER = new ObjectMapper();

    /**
     * Производит удаление пользователя на основе полученных в запросе данных.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            USER_STORE.delete(Integer.parseInt(req.getParameter("id")));
            PrintWriter writer = resp.getWriter();
            SessionDTO loginRole = new SessionDTO();
            HttpSession session = req.getSession();
            loginRole.setLogin((String) session.getAttribute("login"));
            loginRole.setRole((String) session.getAttribute("role"));
            resp.setContentType("application/json");
            CONVERTER.writeValue(writer, loginRole);
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
