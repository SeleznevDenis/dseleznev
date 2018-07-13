package ru.job4j.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Принимает, обрабатывает и отправляет JSON Объекты.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class JsonController extends HttpServlet {
    /**
     * LOG4J логгер.
     */
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Хранилище пользователей.
     */
    private static final Map<Integer, SimpleUser> STORE = new ConcurrentHashMap<>();

    /**
     * Потокобезопасный счетчик.
     */
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    /**
     * Конвертер JSON объектов.
     */
    private static final ObjectMapper CONVERTER = new ObjectMapper();

    /**
     * Отправляет на страницу JSON содержащий всех пользователей из хранилища.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/json");
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            CONVERTER.writeValue(writer, STORE.values());
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Принимает JSON содержащий нового пользователя и добавляет его в хранилище.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String json = req.getReader().lines().collect(Collectors.joining());
            SimpleUser result = CONVERTER.readValue(json, SimpleUser.class);
            STORE.put(COUNTER.incrementAndGet(), result);
            LOG.info(json + " загружены в базу");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Модель данных для JSON конвертера.
     * @author Denis Seleznev
     * @version $Id$
     * @since 0.1
     */
    public static class SimpleUser {
        private String firstName;
        private String lastName;
        private String description;
        private String sex;

        public SimpleUser() {
        }

        public SimpleUser(String firstName, String lastName, String sex, String description) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.sex = sex;
            this.description = description;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
