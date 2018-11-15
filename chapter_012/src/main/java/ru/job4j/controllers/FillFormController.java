package ru.job4j.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.AdvertFormDTO;
import ru.job4j.persistens.dao.CrudDAO;
import ru.job4j.persistens.models.*;
import ru.job4j.utils.SingletonSF;

import java.util.List;

/**
 * Контроллер отправляющий данные для заполнении формы создания объявления.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@RestController
@RequestMapping("/fillAdvert")
public class FillFormController {
    private final SessionFactory sessionFactory = SingletonSF.getSessionFactory();
    private final CrudDAO<Brand> brandDao = new CrudDAO<Brand>(sessionFactory) { };
    private final CrudDAO<Body> bodyDao = new CrudDAO<Body>(sessionFactory) { };
    private final CrudDAO<DriveUnit> driveUnitDao = new CrudDAO<DriveUnit>(sessionFactory) { };
    private final CrudDAO<Engine> engineDao = new CrudDAO<Engine>(sessionFactory) { };
    private final CrudDAO<Transmission> transmissionDao = new CrudDAO<Transmission>(sessionFactory) { };
    private final CrudDAO<Wheel> wheelDao = new CrudDAO<Wheel>(sessionFactory) { };
    private static final Logger LOG = LogManager.getLogger("servlets");


   /**
   * Отправляет json содержащий все данные для формы ввода объявления.
   */
   @GetMapping
    public AdvertFormDTO getFormsData(@RequestParam(value = "action", defaultValue = "") String action) {
        AdvertFormDTO formDTO = new AdvertFormDTO();
        formDTO.setBrands(this.brandDao.findAll());
        if (action.equals("") || !action.equals("filtersForm")) {
            formDTO.setBodies(this.bodyDao.findAll());
            formDTO.setDriveUnits(this.driveUnitDao.findAll());
            formDTO.setEngines(this.engineDao.findAll());
            formDTO.setTransmissions(this.transmissionDao.findAll());
            formDTO.setWheels(this.wheelDao.findAll());
        }
        return formDTO;
    }

    /**
    * Возвращает json содержащий все модели заданной в запросе марки авто.
    */
    @PostMapping(produces = "application/json")
    protected List<Model> findByBrand(@RequestParam(defaultValue = "null") String brandId) {
        LOG.error(brandId);
        LOG.error("findbybrand");
        int brand = brandId.equals("null") ? 1 : Integer.parseInt(brandId);
        return this.brandDao.findById(brand).getModels();
    }
}
