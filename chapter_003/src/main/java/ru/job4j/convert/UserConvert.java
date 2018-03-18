package ru.job4j.convert;

import java.util.HashMap;
import java.util.List;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class UserConvert {
    /**
     * Конвертирует список пользователей list в Map
     * @param list заданный список пользователей.
     * @return HashMap с пользователями User и ключами
     * Id, соответствующими полю id User
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (User element : list) {
            result.put(element.getId(), element);
        }
        return result;
    }
}
