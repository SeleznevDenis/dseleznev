package ru.job4j.servlets;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Хранилище пользователей. Потокобезопасно.
 * Работает на основе шаблона Одиночка, быстрая инициализация.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class MemoryStore implements Store {
    /**
     * Потокобезопасный счетчик.
     */
    private final AtomicInteger counter = new AtomicInteger();
    /**
     * Хранилище пользователей.
     */
    private final Map<Integer, User> userStorage = new ConcurrentHashMap<>();
    private final Map<String, Integer> loginId = new ConcurrentHashMap<>();

    /**
     * Инстанс класса.
     */
    private static final MemoryStore INSTANCE = new MemoryStore();

    private MemoryStore() {
    }

    /**
     * @return Инстанс класса.
     */
    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    /**
     * Добавляет пользователя в систему
     * @param newUser Объект для добавления.
     */
    @Override
    public void add(final User newUser) {
        final int userId = this.counter.incrementAndGet();
        newUser.setId(userId);
        this.userStorage.put(userId, newUser);
        this.loginId.put(newUser.getLogin(), userId);
    }

    /**
     * Обновляет пользователя.
     * @param newUser Объект содержащий данные для обновления и id обновляемого пользователя.
     */
    @Override
    public void update(final User newUser) {
        User replacedUser = this.userStorage.replace(newUser.getId(), newUser);
        this.loginId.remove(replacedUser.getLogin());
        this.loginId.put(newUser.getLogin(), newUser.getId());
    }

    /**
     * Удаляет пользователя из хранилища.
     * @param userId идентификатор удаляемого пользователя.
     */
    @Override
    public void delete(final int userId) {
        User removedUser = this.userStorage.remove(userId);
        if (removedUser != null) {
            this.loginId.remove(removedUser.getLogin());
        }
    }

    /**
     * @return List содержащий всех пользователей в хранилище.
     */
    @Override
    public List<User> findAll() {
        return new ArrayList<>(this.userStorage.values());
    }

    /**
     * Ищет пользователя по идентификатор.
     * @param id идентификатор пользователя.
     * @return найденный пользователь.
     */
    @Override
    public User findById(final int id) {
        return this.userStorage.get(id);
    }

    @Override
    public User findByLogin(String login) {
        return this.userStorage.get(this.loginId.get(login));
    }
}
