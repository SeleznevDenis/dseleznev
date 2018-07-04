package ru.job4j.servlets;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Объект класса проверяет возможность выполнения запросов
 * и передает их для выполнения в MemoryStore
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class ValidateService {
    /**
     * Ссылка на обертку над хранилищем пользователей
     */
    private final Store store = DbStore.getInstance();

    /**
     * Инстанс ValidateService.
     */
    private static final ValidateService INSTANCE = new ValidateService();

    private ValidateService() {
    }

    /**
     * @return инстанс класса.
     */
    public static ValidateService getInstance() {
        return INSTANCE;
    }

    /**
     * Добавляет пользователя в систему.
     * @param newUser пользователь для добавления.
     */
    public boolean add(final User newUser) {
        boolean done = false;
        if (newUser != null
                && newUser.getName() != null
                && newUser.getLogin() != null
                && newUser.getEmail() != null
                && !newUser.getName().equals("")
                && !newUser.getLogin().equals("")
                && !newUser.getEmail().equals("")) {
            Calendar currentDate = new GregorianCalendar();
            currentDate.setTime(new Date());
            newUser.setCreateDate(currentDate);
            this.store.add(newUser);
            done = true;
        }
        return done;
    }

    /**
     * Обновляет пользователя в системе, если это возможно.
     * @param newUser данные для обновления.
     * @return boolean результат операции.
     */
    public boolean update(final User newUser) {
        boolean done = false;
        if (this.store.findById(newUser.getId()) != null
                && newUser.getName() != null
                && newUser.getLogin() != null
                && newUser.getEmail() != null) {
            this.store.update(newUser);
            done = true;
        }
        return done;
    }

    /**
     * Удаляет пользователя из системы, если это возможно.
     * @param id идентификатор удаляемого пользователя.
     * @return boolean результат операции.
     */
    public boolean delete(final int id) {
        boolean done = false;
        if (this.store.findById(id) != null) {
            this.store.delete(id);
            done = true;
        }
        return done;

    }

    /**
     * @return всех пользователей из системы.
     */
    public List<User> findAll() {
        return this.store.findAll();
    }

    /**
     * Ищет пользователя по идентификатору.
     * @param id идентификатор пользователя.
     * @return найденный пользователь.
     */
    public User findById(final int id) {
        return this.store.findById(id);
    }
}
