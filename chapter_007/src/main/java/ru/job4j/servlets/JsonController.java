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
    private static final Map<Integer, UserDTO> STORE = new ConcurrentHashMap<>();

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
            UserDTO result = CONVERTER.readValue(json, UserDTO.class);
            STORE.put(COUNTER.incrementAndGet(), result);
            LOG.info(json + " загружены в базу");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }


}
