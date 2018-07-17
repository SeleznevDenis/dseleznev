package ru.job4j.servlets;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Отвечает за списки городов и стран передаваемые виду.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AddressController extends HttpServlet {

    /**
     * Логгер.
     */
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Хранилище городов и стран.
     */
    private static final UserAddressStore ADDRESS_STORE = DbStore.getInstance();

    /**
     * Json конвертер.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Отправляет на вид список стран.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            MAPPER.writeValue(writer, ADDRESS_STORE.findAllCountries());
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Получает название страны, и отправляет соответствующие ей города.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        MAPPER.writeValue(writer, ADDRESS_STORE.findCitiesByCountry(
                req.getParameter("country")
        ));
        writer.flush();
    }
}
