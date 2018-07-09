package ru.job4j.servlets;

import java.util.List;

/**
 * Интерфейс для работы с хранилищем данных.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public interface Store {
    /**
     * Добавляет пользователя в хранилище.
     * @param user пользователь для добавления.
     */
    void add(User user);

    /**
     * Обновляет пользователя в хранилище
     * @param newUser данные для обновления.
     */
    void update(User newUser);

    /**
     * Удаляет пользователя из хранилища.
     * @param userId идентификатор удаляемого пользователя.
     */
    void delete(int userId);

    /**
     * @return список пользователей из хранилища.
     */
    List<User> findAll();

    /**
     * Ищет пользователя по идентификатору.
     * @param id идентификатор пользователя.
     * @return найденный пользователь.
     */
    User findById(int id);

    /**
     * Ищет пользователя по логину.
     * @param login логин искомого пользователя.
     * @return найденный пользователь.
     */
    User findByLogin(String login);
}
