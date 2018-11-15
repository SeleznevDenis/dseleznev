package ru.job4j.persistens.dao;

import org.hibernate.SessionFactory;
import ru.job4j.dto.FilterDTO;
import ru.job4j.persistens.models.Advert;

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
        System.out.println(filter.getBrand() == null);
        return this.getWrapper().wrapAndExecute(session ->
            session.createQuery(
                    "from Advert as a "
                            + "left join fetch a.model AS m "
                            + "left join fetch a.body "
                            + "left join fetch a.driveUnit "
                            + "left join fetch a.engine "
                            + "left join fetch a.transmission "
                            + "left join fetch a.wheel "
                            + "where (:brandFilter = true or m.brand = :brand) AND"
                            + "(:photoFilter = true or a.photo is not null) AND"
                            + "(:dateStartFilter = true or a.advertDate > :dateStart) AND"
                            + "(:dateFinishFilter = true or a.advertDate < :dateFinish)",
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
