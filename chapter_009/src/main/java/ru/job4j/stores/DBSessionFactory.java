package ru.job4j.stores;

import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Session Factory Singleton.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class DBSessionFactory {
    private static final Logger LOG = LogManager.getLogger("servlets");
    private static SessionFactory factory;

    static {
        init();
    }

    private DBSessionFactory() {
    }

    /**
     * Инициализирует session factory.
     */
    private static void init() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Закрывает Session Factory.
     */
    public static synchronized void close() {
        if (factory != null) {
            factory.close();
        }
    }

    /**
     * @return Session Factory.
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (factory == null) {
            init();
        }
        return factory;
    }
}
