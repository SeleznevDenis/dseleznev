package ru.job4j.carplace.listeners;

import ru.job4j.utils.SingletonSF;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Закрывает фабрику сессий при уничтожении сервлет контекста.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class ConxListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SingletonSF.close();
    }
}
