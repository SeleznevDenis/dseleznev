package ru.job4j.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.AuthDTO;
import ru.job4j.persistens.dao.UserDao;
import ru.job4j.persistens.models.User;
import ru.job4j.utils.SingletonSF;

import javax.servlet.http.HttpSession;

/**
 * Контроллер авторизации.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private final UserDao userDao = new UserDao(SingletonSF.getSessionFactory());

    @GetMapping
    public AuthDTO getAuth(HttpSession session) {
        AuthDTO dto = new AuthDTO();
        dto.setLogin((String) session.getAttribute("login"));
        return dto;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public AuthDTO postAuth(@RequestBody AuthDTO inputDTO, HttpSession session) {
        AuthDTO outputDTO = new AuthDTO();
        if (inputDTO.getMessage().equals("signIn")) {
            String login = inputDTO.getLogin();
            String password = inputDTO.getPassword();
            User foundUsr = this.userDao.findUserByLogin(login);
            if (foundUsr != null && password.equals(foundUsr.getPassword())) {
                session.setAttribute("login", login);
                session.setAttribute("id", foundUsr.getId());
                LOG.error("userId " + foundUsr.getId());
                outputDTO.setLogin(login);
            } else {
                outputDTO.setMessage("invalid login or password");
            }

        } else if (inputDTO.getMessage().equals("signOut")) {
            session.invalidate();
        }
        return outputDTO;
    }
}