package ru.job4j.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.models.Task;
import ru.job4j.stores.TaskStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Task Controller.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class TasksController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private static final TaskStore STORE = new TaskStore();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Отправляет json содержащий все задачи.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Writer writer = resp.getWriter();
            resp.setContentType("application/json");
            MAPPER.writeValue(writer, STORE.findAll());
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Принимает json и добавляет задачу.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            STORE.add(MAPPER.readValue(req.getReader(), Task.class));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
