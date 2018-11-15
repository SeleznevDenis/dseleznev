package ru.job4j.persistens.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import ru.job4j.persistens.models.User;

/**
 * Расширяет UserDao добавляя метод поиска юзера по логину.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserDao extends CrudDAO<User> {
    private static final Logger LOG = LogManager.getLogger("servlets");
    public UserDao(SessionFactory sf) {
        super(sf);
    }

    /**
     * Ищет юзера по логину.
     * @param login логин для поиска.
     * @return найденный юзер.
     */
    public User findUserByLogin(String login) {
        return getWrapper().wrapAndExecute(session -> {
                    User result = null;
                    try {
                        result = session.createQuery("from User where login = :login", User.class)
                                .setParameter("login", login).getSingleResult();
                    } catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                    }
                    return result;
                }
        );
    }
}
