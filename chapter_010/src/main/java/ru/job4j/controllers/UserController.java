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
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Сервлет отвечащий за пользователей системы.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final UserDao USER_DAO = new UserDao(SingletonSF.getSessionFactory()) { };

    /**
     * Возвращает json содержащий данные пользователя, соответствующие id переданному в запросе.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userDTO = USER_DAO.findById(Integer.parseInt(req.getParameter("id")));
        if (userDTO != null) {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            MAPPER.writeValue(writer, userDTO);
            writer.flush();
        }
    }

    /**
     * В зависимоти от запроса регистрирует, обновляет, либо удаляет пользователя системы.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        User user = MAPPER.readValue(req.getParameter("json"), User.class);
        AuthDTO dto = new AuthDTO();
        if (action.equals("create")) {
            if (USER_DAO.findUserByLogin(user.getLogin()) == null) {
                USER_DAO.add(user);
            } else {
                dto.setMessage("Login already in use");
            }
            USER_DAO.add(user);
        } else if (action.equals("update")) {
            USER_DAO.update(user);
        } else if (action.equals("delete")) {
            USER_DAO.delete(user);
        }
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        MAPPER.writeValue(writer, dto);
        writer.flush();
    }
}
