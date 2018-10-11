package ru.job4j.carplace.persistens.dao;

import org.hibernate.SessionFactory;
import ru.job4j.carplace.dto.FilterDTO;
import ru.job4j.carplace.persistens.models.Advert;


import java.util.List;

/**
 * Advert Data Acess Object
 * @author Denis Seieznev
 * @version $Id$
 * @since 0.1
 */
public class AdvertDAO extends CrudDAO<Advert> {
    public AdvertDAO(SessionFactory sf) {
        super(sf);
    }

    /**
     * Возвращает список объявлений с учетом объекта фильтра.
     * @param filter объект фильтр
     * @return список объявлений.
     */
    public List<Advert> findByFilter(FilterDTO filter) {
        System.out.println(filter);
        return this.getWrapper().wrapAndExecute(session ->
            session.createQuery(
                    "from Advert AS a join fetch a.model AS m "
                            + "join fetch a.body "
                            + "join fetch a.driveUnit "
                            + "join fetch a.engine "
                            + "join fetch a.transmission "
                            + "join fetch a.wheel "
                            + "where ((:brandFilter = true or m.brand = :brand) "
                            + "AND (:photoFilter = true or a.photo is not null) "
                            + "AND (:dateStartFilter = true or a.advertDate > :dateStart) "
                            + "AND (:dateFinishFilter = true or a.advertDate < :dateFinish))",
                    Advert.class
            ).setParameter("brandFilter", filter.getBrand() == null)
            .setParameter("brand", filter.getBrand())
            .setParameter("photoFilter", !filter.isWithPhoto())
            .setParameter("dateStartFilter", filter.getStartDate() == null)
            .setParameter("dateStart", filter.getStartDate())
            .setParameter("dateFinishFilter", filter.getFinishDate() == null)
            .setParameter("dateFinish", filter.getFinishDate())
            .list()
        );
    }
}
