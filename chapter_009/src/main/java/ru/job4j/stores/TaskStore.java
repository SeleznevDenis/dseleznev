package ru.job4j.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.models.Task;

import java.util.List;

/**
 * Task Store.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class TaskStore {
    private static final SessionFactory FACTORY = DBSessionFactory.getSessionFactory();

    /**
     * Закрывает Session Factory.
     */
    public void close() {
        FACTORY.close();
    }

    /**
     * Добавляет задачу в базу данных.
     * @param task задача для добавления.
     */
    public void add(Task task) {
        Session session = FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(task);
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    /**
     * Удаляет задачу из базы данных по идентификатору.
     * @param id идентификатор задачи.
     */
    public void delete(int id) {
        Session session = FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(new Task(id));
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    /**
     * Обновляет задачу в базе данных.
     * @param task данные для обновления.
     */
    public void update(Task task) {
        Session session = FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(task);
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    /**
     * @return все задачи из базы данных.
     */
    public List<Task> findAll() {
        Session session = FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        List<Task> result = null;
        try {
            result = session.createQuery("from Task", Task.class).list();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.commit();
            session.close();
        }
        return result;
    }

    /**
     * Ищет задачу по идентификатору.
     * @param id идентификатор искомой задачи.
     * @return найденная задача.
     */
    public Task findById(int id) {
        Task result = null;
        Session session = FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.get(Task.class, id);
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.commit();
            session.close();
        }
        return result;
    }
}
