package ru.job4j.listeners;

import ru.job4j.stores.DBSessionFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * Context Listener.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public final class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("start");
    }

    /**
     * Закрывает Session Factory.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBSessionFactory.close();
    }
}
