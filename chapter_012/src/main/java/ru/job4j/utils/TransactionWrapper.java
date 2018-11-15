package ru.job4j.utils;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Function;

/**
 * Hibernate transaction wrapper.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class TransactionWrapper {
    private final Logger logger;
    private final SessionFactory sf;

    public TransactionWrapper(Logger logger, SessionFactory sf) {
        this.sf = sf;
        this.logger = logger;
    }

    /**
     * Обёртка транзакций.
     * @param command функция содержащая действия во время транзацкии.
     * @param <T> Параметрический тип возвращаемого значения функции.
     * @return результат работы функции
     */
    public <T> T wrapAndExecute(final Function<Session, T> command) {
        final Session session = this.sf.openSession();
        final Transaction transaction = session.beginTransaction();
        T rst;
        try {
            rst = command.apply(session);
            transaction.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            session.close();
        }
        return rst;
    }
}
