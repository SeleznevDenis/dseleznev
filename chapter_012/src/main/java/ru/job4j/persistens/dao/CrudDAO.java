package ru.job4j.persistens.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import ru.job4j.persistens.interfaces.IEntity;
import ru.job4j.utils.TransactionWrapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Абстрактный универсальный DAO с CRUD операциями.
 *
 * При наследовании необходимо задать тип генерика в родителе
 * и с данным типом будут производиться все операции.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 * @param <T> Тип сущности с которым будет работать DAO.
 */
public abstract class CrudDAO<T extends IEntity> {
    private static final Logger LOGGER = LogManager.getLogger("servlets");

    /**
     * Обертка транзакций.
     */
    private final TransactionWrapper wrapper;

    /**
     * Сохраняет класс генерика.
     */
    private Class genericClass;

    public CrudDAO(SessionFactory sf) {
        this.wrapper = new TransactionWrapper(LOGGER, sf);
        this.genericClass = (Class)
                ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Сохраняет сущность в базу данных.
     * @param entity сущность для сохранения.
     * @return id сущности после сохранения.
     */
    public int add(T entity) {
        return this.wrapper.wrapAndExecute(session -> {
            session.save(entity);
            return entity.getId();
        });
    }

    /**
     * Удаляет сущность из базы данных.
     * @param entity удаляемая сущность.
     */
    public void delete(T entity) {
        this.wrapper.wrapAndExecute(session -> {
            session.delete(entity);
            return null;
        });
    }

    /**
     * Обновляет сущность в базе данных
     * @param entity новые данные сущности.
     */
    public void update(T entity) {
        this.wrapper.wrapAndExecute(session -> {
            session.update(entity);
            return null;
        });
    }

    /**
     * Ищет сущность в базе данных по идентификатору.
     * @param id идентификатор искомой сущности.
     * @return искомая сущность.
     */
    @SuppressWarnings("unchecked")
    public T findById(int id) {
        return (T) this.wrapper.wrapAndExecute(session -> session.get(this.genericClass, id));
    }

    /**
     * @return все сущности заданного типа из базы данных.
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return (List<T>) this.wrapper.wrapAndExecute(session ->
                session.createQuery("from " + this.genericClass.getSimpleName(), this.genericClass).list()
        );
    }

    /**
     * @return класс модели, которую использует dao.
     */
    protected Class getModelClass() {
        return this.genericClass;
    }

    /**
     * @return transaction wrapper.
     */
    protected TransactionWrapper getWrapper() {
        return this.wrapper;
    }
}
