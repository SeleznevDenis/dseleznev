package ru.job4j.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carplace.dto.AddAdvertDTO;
import ru.job4j.carplace.dto.FilterDTO;
import ru.job4j.carplace.persistens.dao.AdvertDAO;
import ru.job4j.carplace.persistens.dao.CrudDAO;
import ru.job4j.carplace.persistens.models.Advert;
import ru.job4j.utils.SingletonSF;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Контроллер объявлений.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AdvertController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final AdvertDAO ADVERT_DAO = new AdvertDAO(SingletonSF.getSessionFactory());

    /**
     * Возвращает json содержащий все объявления.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String action = req.getParameter("action");
        List<Advert> result = null;
        if (action == null) {
            result = ADVERT_DAO.findAll();
        } else if (action.equals("filter")) {
            FilterDTO filter = MAPPER.readValue(req.getParameter("filter"), FilterDTO.class);
            result = ADVERT_DAO.findByFilter(filter);
        }
        MAPPER.writeValue(writer, result);
        writer.flush();
    }

    /**
     * В зависимости от содержания запроса добавляет, обновляет или удаляет объявление из базы.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AddAdvertDTO dto = MAPPER.readValue(req.getReader(), AddAdvertDTO.class);
        String action = dto.getAction();
        Advert advert = dto.getAdvert();
        advert.setId(Integer.parseInt((String) req.getSession().getAttribute("id")));
        LOG.info(action);
        LOG.info(advert.getBody());
        if (action.equals("create")) {
            ADVERT_DAO.add(advert);
        } else if (action.equals("update")) {
            ADVERT_DAO.update(advert);
        } else if (action.equals("delete")) {
            ADVERT_DAO.delete(advert);
        }
    }
}
