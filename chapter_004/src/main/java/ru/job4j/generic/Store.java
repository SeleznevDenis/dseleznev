package ru.job4j.generic;

/**
 * Интерфейс Store, для работы с хранилищами UserStore и RoleStore.
 * @author Denis Seleznev
 * @version $Id$
 * @since 31.03.2018
 */
public interface Store<T extends Base> {

    void add(T model);

    boolean replace(String id, T model);

    boolean delete(String id);

    T findById(String id);
}
