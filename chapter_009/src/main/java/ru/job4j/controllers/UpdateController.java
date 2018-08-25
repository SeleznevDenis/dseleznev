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

/**
 * Update Controller.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UpdateController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private static final TaskStore STORE = new TaskStore();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Принимает json содержащий задачу и производит её обновление в хранилище.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Task updatedTask = MAPPER.readValue(req.getReader(), Task.class);
            STORE.update(updatedTask);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Закрывает Session Factory.
     */
    @Override
    public void destroy() {
        STORE.close();
    }
}
