package ru.job4j.musical.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.musical.stores.MusicTypeStore;
import ru.job4j.musical.stores.RoleStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Заполнитель селектов.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class SelectFillerController extends HttpServlet {
    /**
     * LOG4J Логгер.
     */
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Хранилище музыкальных типов.
     */
    private static final MusicTypeStore MUSIC_TYPE_STORE = new MusicTypeStore();

    /**
     * Хранилище ролей.
     */
    private static final RoleStore ROLE_STORE = new RoleStore();

    /**
     * JSON конвертер.
     */
    private static final ObjectMapper CONVERTER = new ObjectMapper();

    /**
     * В зависимости от запроса возвращает список ролей или музыкальных типов на страницу.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        if (action != null) {
            try {
                PrintWriter writer = resp.getWriter();
                resp.setContentType("application/json");
                if (action.equals("type")) {
                    CONVERTER.writeValue(writer, MUSIC_TYPE_STORE.findAll());
                } else if (action.equals("role")) {
                    CONVERTER.writeValue(writer, ROLE_STORE.findAll());
                }
                writer.flush();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
