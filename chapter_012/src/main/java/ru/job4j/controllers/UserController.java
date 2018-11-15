package ru.job4j.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.AuthDTO;
import ru.job4j.persistens.dao.UserDao;
import ru.job4j.persistens.models.User;
import ru.job4j.utils.SingletonSF;
import java.io.IOException;

/**
 * Сервлет отвечащий за пользователей системы.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final UserDao USER_DAO = new UserDao(SingletonSF.getSessionFactory()) { };


    /**
     * Возвращает json содержащий данные пользователя, соответствующие id переданному в запросе.
     */
    @GetMapping(produces = "application/json")
    protected User findUser(@RequestParam int id) {
        User result = new User();
        User userDTO = USER_DAO.findById(id);
        if (userDTO != null) {
            result = userDTO;
        }
        return result;
    }


    /**
     * В зависимоти от запроса регистрирует, обновляет, либо удаляет пользователя системы.
     */
    @PostMapping(produces = "application/json")
    protected AuthDTO handleUser(@RequestParam String action, @RequestParam String json) throws IOException {
        User user = MAPPER.readValue(json, User.class);
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
        return dto;
    }
}

