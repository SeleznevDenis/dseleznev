package ru.job4j.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carplace.dto.AuthDTO;
import ru.job4j.carplace.persistens.dao.UserDao;
import ru.job4j.carplace.persistens.models.User;
import ru.job4j.utils.SingletonSF;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Контроллер авторизации.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AuthController extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger("servlets");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final UserDao USER_DAO = new UserDao(SingletonSF.getSessionFactory());

    /**
     * Отправляет json содержащий логин зарегистрированный в сессии.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AuthDTO dto = new AuthDTO();
        dto.setLogin((String) session.getAttribute("login"));
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        MAPPER.writeValue(writer, dto);
        writer.flush();
    }

    /**
     * Авторизует пользователя, если логин и пароль совпадают с хранящимися в базе, либо закрывает сессию.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthDTO inputDTO = MAPPER.readValue(req.getReader(), AuthDTO.class);
        HttpSession session = req.getSession();
        AuthDTO outputDTO = new AuthDTO();
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        if (inputDTO.getMessage().equals("signIn")) {
            String login = inputDTO.getLogin();
            String password = inputDTO.getPassword();
            User foundUsr = USER_DAO.findUserByLogin(login);
            if (foundUsr != null && password.equals(foundUsr.getPassword())) {
                session.setAttribute("login", login);
                session.setAttribute("id", foundUsr.getId());
                outputDTO.setLogin(login);
            } else {
                outputDTO.setMessage("invalid login or password");
            }

        } else if (inputDTO.getMessage().equals("signOut")) {
            session.invalidate();
        }
        MAPPER.writeValue(writer, outputDTO);
        writer.flush();
    }
}
