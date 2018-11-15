package ru.job4j.persistens.dao;

import org.junit.Test;
import ru.job4j.dto.FilterDTO;
import ru.job4j.persistens.models.Advert;
import ru.job4j.utils.SingletonSF;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AdvertDAOTest extends CrudDAOTest<Advert> {

    @Override
    protected AdvertDAO getTestDao() {
        return new AdvertDAO(SingletonSF.getSessionFactory());
    }

    @Override
    protected Advert getTestEntity() {
        Advert result = new Advert();
        result.setDescription("test");
        return result;
    }

    @Override
    protected Advert getSecondTestEntity() {
        Advert result = new Advert();
        result.setDescription("test2");
        result.setPhoto("test");
        result.setAdvertDate(new Timestamp(new Date().getTime() + 100L));
        return result;
    }

    @Test
    public void findByPhotoFilter() {
        Advert firstEntity = this.getTestEntity();
        firstEntity.setId(this.getTestDao().add(firstEntity));
        Advert secondEntity = this.getSecondTestEntity();
        secondEntity.setId(this.getTestDao().add(secondEntity));
        FilterDTO filter = new FilterDTO();
        filter.setWithPhoto(true);
        List<Advert> result = this.getTestDao().findByFilter(filter);
        assertTrue(result.contains(secondEntity));
        assertFalse(result.contains(firstEntity));
    }

    @Test
    public void findByDateFilter() {
        FilterDTO filter = new FilterDTO();
        filter.setStartDate(new Timestamp(new Date().getTime()));
        Advert entity = this.getSecondTestEntity();
        entity.setId(this.getTestDao().add(entity));
        assertThat(this.getTestDao().findByFilter(filter).get(0), is(entity));
    }
}