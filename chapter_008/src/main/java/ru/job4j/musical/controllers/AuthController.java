package ru.job4j.musical.controllers;

import com.fasterxml.jackson.core.JsonParseException;
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
 * Auth Controller.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AuthController extends HttpServlet {
    /**
     * LOG4j логгер.
     */
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Хранилище пользователей.
     */
    private static final UserStore USER_STORE = new UserStore();

    /**
     * JSON конвертер.
     */
    private static final ObjectMapper CONVERTER = new ObjectMapper();

    /**
     * Отправляет логин и роль пользователя на страницу.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            String login = (String) session.getAttribute("login");
            String role = (String) session.getAttribute("role");
            String message = (String) session.getAttribute("message");
            SessionDTO json = new SessionDTO(login, role);
            json.setMessage(message);
            resp.setContentType("application/json");
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            CONVERTER.writeValue(writer, json);
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Регистрируют пользователя в системе либо уничтожает сессию.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            SessionDTO sessionDTO = CONVERTER.readValue(req.getReader(), SessionDTO.class);
            HttpSession session = req.getSession();
            if (sessionDTO.getMessage().equals("signIn")) {
                String login = sessionDTO.getLogin();
                String password = sessionDTO.getPassword();
                User foundUser = USER_STORE.findByLogin(login);
                if (foundUser != null && foundUser.getPassword().equals(password)) {
                    session.setAttribute("login", login);
                    session.setAttribute("role", foundUser.getRole().getRole());
                } else {
                    session.setAttribute("message", "Incorrect user or password");
                }
            } else if (sessionDTO.getMessage().equals("signOut")) {
                session.invalidate();
            }
        } catch (JsonParseException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}