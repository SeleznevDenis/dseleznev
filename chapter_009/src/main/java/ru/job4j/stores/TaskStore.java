package ru.job4j.stores;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.models.Task;

import java.util.List;
import java.util.function.Function;

/**
 * Task Store.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class TaskStore {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private static final SessionFactory FACTORY = DBSessionFactory.getSessionFactory();

    /**
     * Закрывает Session Factory.
     */
    public void close() {
        FACTORY.close();
    }


    /**
     * Удаляет задачу из базы данных по идентификатору.
     * @param id идентификатор задачи.
     */
    public boolean delete(final int id) {
         return this.tx(
                session -> {
                    boolean result = false;
                    if (this.findById(id) != null) {
                        session.delete(new Task(id));
                        result = true;
                    }
                    return result;
                }
        );
    }

    /**
     * Обёртка транзакций.
     * @param command функция содержащая действия во время транзацкии.
     * @param <T> Параметрический тип возвращаемого значения функции.
     * @return результат работы функции
     */
    private <T> T tx(final Function<Session, T> command) {
        final Session session = FACTORY.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            transaction.commit();
            session.close();
        }
    }

    /**
     * Добавляет задачу в базу данных.
     * @param task задача для добавления.
     */
    public void add(Task task) {
        this.tx(session -> {
            session.save(task);
            return null;
        });
    }

    /**
     * Обновляет задачу в базе данных.
     * @param task данные для обновления.
     */
    public void update(Task task) {
        this.tx(session -> {
            session.update(task);
            return null;
        });
    }

    /**
     * @return все задачи из базы данных.
     */
    public List<Task> findAll() {
        return this.tx(session -> session.createQuery("from Task", Task.class).list());
    }

    /**
     * Ищет задачу по идентификатору.
     * @param id идентификатор искомой задачи.
     * @return найденная задача.
     */
    public Task findById(int id) {
        return this.tx(session -> session.get(Task.class, id));
    }
}
