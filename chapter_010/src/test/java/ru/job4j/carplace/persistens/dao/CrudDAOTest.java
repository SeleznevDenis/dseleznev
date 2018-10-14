package ru.job4j.carplace.persistens.dao;

import org.junit.Test;
import ru.job4j.carplace.persistens.interfaces.IEntity;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public abstract class CrudDAOTest<T extends IEntity> {

    protected abstract CrudDAO<T> getTestDao();
    protected abstract T getTestEntity();
    protected abstract T getSecondTestEntity();

    @Test
    public void add() {
        int entityId = this.getTestDao().add(this.getTestEntity());
        T resultEntity = this.getTestDao().findById(entityId);
        resultEntity.setId(0);
        assertThat(resultEntity, is(this.getTestEntity()));
    }

    @Test
    public void delete() {
        int entityId = this.getTestDao().add(this.getTestEntity());
        T entity = this.getTestEntity();
        entity.setId(entityId);
        this.getTestDao().delete(entity);
        assertNull(this.getTestDao().findById(entityId));
    }

    @Test
    public void update() {
        int entityId = this.getTestDao().add(this.getTestEntity());
        T upEntity = this.getSecondTestEntity();
        upEntity.setId(entityId);
        this.getTestDao().update(upEntity);
        assertThat(this.getTestDao().findById(entityId), is(upEntity));
    }
}