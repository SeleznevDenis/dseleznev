package ru.job4j.utils;

import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Session factory singleton
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SingletonSF {
    private static final Logger LOGGER = LogManager.getLogger("servlets");
    private static final SessionFactory FACTORY;
    private static final StandardServiceRegistry REGISTRY;

    static {
        REGISTRY = new StandardServiceRegistryBuilder().configure().build();
        try {
            FACTORY = new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(REGISTRY);
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    private SingletonSF() {
    }

    /**
     * Закрывает Session Factory.
     */
    public static void close() {
        if (!FACTORY.isClosed()) {
            try {
                StandardServiceRegistryBuilder.destroy(REGISTRY);
                FACTORY.close();
            } catch (IllegalStateException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * @return Session Factory.
     */
    public static SessionFactory getSessionFactory() {
        return FACTORY;
    }
}