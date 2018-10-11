package ru.job4j.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import ru.job4j.carplace.dto.AdvertFormDTO;
import ru.job4j.carplace.persistens.dao.CrudDAO;
import ru.job4j.carplace.persistens.models.*;
import ru.job4j.utils.SingletonSF;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Контроллер отправляющий данные для заполнении формы создания объявления.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class FillFormController extends HttpServlet {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SessionFactory S_FACTORY = SingletonSF.getSessionFactory();
    private static final CrudDAO<Brand> BRAND_DAO = new CrudDAO<Brand>(S_FACTORY) { };
    private static final CrudDAO<Body> BODY_DAO = new CrudDAO<Body>(S_FACTORY) { };
    private static final CrudDAO<DriveUnit> DRIVE_UNIT_DAO = new CrudDAO<DriveUnit>(S_FACTORY) { };
    private static final CrudDAO<Engine> ENGINE_DAO = new CrudDAO<Engine>(S_FACTORY) { };
    private static final CrudDAO<Transmission> TRANSMISSION_DAO = new CrudDAO<Transmission>(S_FACTORY) { };
    private static final CrudDAO<Wheel> WHEEL_DAO = new CrudDAO<Wheel>(S_FACTORY) { };
    private static final Logger LOG = LogManager.getLogger("servlets");


    /**
     * Отправляет json содержащий все данные для формы ввода объявления.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdvertFormDTO formDTO = new AdvertFormDTO();
        formDTO.setBrands(BRAND_DAO.findAll());
        String action = req.getParameter("action");
        if (action == null || !req.getParameter("action").equals("filtersForm")) {
            formDTO.setBodies(BODY_DAO.findAll());
            formDTO.setDriveUnits(DRIVE_UNIT_DAO.findAll());
            formDTO.setEngines(ENGINE_DAO.findAll());
            formDTO.setTransmissions(TRANSMISSION_DAO.findAll());
            formDTO.setWheels(WHEEL_DAO.findAll());
        }
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        MAPPER.writeValue(writer, formDTO);
        LOG.info(MAPPER.writeValueAsString(formDTO));
        writer.flush();
    }

    /**
     * Возвращает json содержащий все модели заданной в запросе марки авто.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brandId");
        int brandId = brand == null ? 1 : Integer.parseInt(brand);
        List<Model> models = BRAND_DAO.findById(brandId).getModels();
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        MAPPER.writeValue(writer, models);
        writer.flush();
    }
}
