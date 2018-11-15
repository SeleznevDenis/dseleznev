package ru.job4j.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import ru.job4j.dto.AddAdvertDTO;
import ru.job4j.dto.FilterDTO;
import ru.job4j.persistens.dao.AdvertDAO;
import ru.job4j.persistens.models.Advert;
import ru.job4j.utils.SingletonSF;
import java.io.IOException;
import java.util.List;

/**
 * Контроллер объявлений.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@RestController
@RequestMapping("/advert")
public class AdvertController {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private final AdvertDAO advertDAO;
    private final ObjectMapper mapper;

    public AdvertController() {
        this.advertDAO = new AdvertDAO(SingletonSF.getSessionFactory());
        this.mapper = new ObjectMapper();
    }

    @RequestMapping(method = RequestMethod.GET, headers = {"Content-type=application/json"})
    public List<Advert> findAdverts(@RequestParam String filter) throws IOException {
        List<Advert> result;
        if (filter.equals("")) {
            result = this.advertDAO.findAll();
        } else {
            result = this.advertDAO.findByFilter(this.mapper.readValue(filter, FilterDTO.class));
        }
        return result;
    }

    @PostMapping(consumes = "application/json")
    public void postAdverts(@RequestBody AddAdvertDTO dto) {
        LOG.error("aadfasdfasdfasdf");
        String action = dto.getAction();
        Advert advert = dto.getAdvert();
        LOG.error("in advert Add");
        LOG.error(action);
        LOG.error(advert);
        if (action.equals("create")) {
            this.advertDAO.add(advert);
        } else if (action.equals("update")) {
            this.advertDAO.update(advert);
        } else if (action.equals("delete")) {
            this.advertDAO.delete(advert);
        }
    }
}
