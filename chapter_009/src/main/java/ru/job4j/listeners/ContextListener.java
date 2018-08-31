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
public class ContextListener implements ServletContextListener {

    /**
     * Закрывает Session Factory.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBSessionFactory.close();
    }
}
