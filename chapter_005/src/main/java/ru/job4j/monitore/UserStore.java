package ru.job4j.monitore;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

/**
 * Потокобезопасное, простое хранилище объектов типа User.
 * @author Denis Seleznev
 * @version $Id$
 * @since 27.04.2018
 */
@ThreadSafe
public class UserStore {
    /**
     * Главное хранилище объектов.
     */
    @GuardedBy("this")
    private Map<Integer, User> mainStorage = new HashMap<>();

    /**
     * Вводит объект в хранилище.
     * @param user объект для ввода в хранилище.
     * @return успешность операции.
     */
    public synchronized boolean add(User user) {
        return user != null && mainStorage.putIfAbsent(user.getId(), user) == null;
    }

    /**
     * Обновляет объект с совпадающим id заданного user, новым значением user.
     * @param user заданный для обновления объект.
     * @return успешность операции.
     */
    public synchronized boolean update(User user) {
        User check = null;
        if (user != null) {
            check = this.mainStorage.replace(user.getId(), user);
        }
        return check != null;
    }

    /**
     * Удаляет объект из хранилища.
     * @param user заданный для удаления объект.
     * @return успешность операции.
     */
    public synchronized boolean delete(User user) {
        return mainStorage.remove(user.getId(), user);
    }

    /**
     * Переводит средства value от одного User к другому.
     * @param fromId Id User со счета которого выполняется перевод.
     * @param told Id User на счет которого выполняется перевод.
     * @param amount величина перевода.
     * @return успешность операции.
     */
    public synchronized boolean transfer(Integer fromId, Integer told, int amount) {
        boolean done = false;
        if (amount > 0 && mainStorage.containsKey(fromId) && mainStorage.containsKey(told)) {
            User fromUser = mainStorage.get(fromId);
            User toldUser = mainStorage.get(told);
            if (fromUser.getAmount() >= amount) {
                mainStorage.put(fromId, new User(fromId, fromUser.getAmount() - amount));
                mainStorage.put(told, new User(told, toldUser.getAmount() + amount));
                done = true;
            }
        }
        return done;
    }

    /**
     * Возвращает User из хранилища с заданным id.
     * @param id заданный id.
     * @return User с заданным id.
     */
    public synchronized User findUserById(Integer id) {
        return this.mainStorage.get(id);
    }
}
