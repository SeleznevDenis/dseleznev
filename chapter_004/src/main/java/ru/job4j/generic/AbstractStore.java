package ru.job4j.generic;

import java.util.NoSuchElementException;

/**
 * Релизует методы для хранилищ: UserStore и RoleStore.
 * @author Denis Seleznev
 * @version $Id$
 * @since 31.03.2018
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {

    private SimpleArray<T> simpleArray = new SimpleArray<>();

    /**
     * Добавляет объект в хранилище.
     * @param model объект.
     */
    @Override
    public void add(T model) {
        simpleArray.add(model);
    }

    /**
     * Производит поиск объекта в хранилище по id.
     * @param id заданный для поиска.
     * @return индекс объекта в хранилище, либо -1, если объекта нет.
     */
    private int findObjectIndexById(String id) {
        int foundIndex = -1;
        for (int i = 0; i < simpleArray.size(); i++) {
            if (simpleArray.get(i).getId().equals(id)) {
                foundIndex = i;
                break;
            }
        }
        return foundIndex;
    }

    /**
     * Вводит объект в хранилище на место объекта с заданным id.
     * @param id заданный id.
     * @param model объект для замены.
     * @return true - если замена проведена успешна.
     *         false - если замена не удалась.
     */
    @Override
    public boolean replace(String id, T model) {
        int objectIndex = findObjectIndexById(id);
        if (objectIndex == -1) {
            return false;
        }
        simpleArray.set(objectIndex, model);
        return true;
    }

    /**
     * Удаляет объект из хранилища по заданному id объекта.
     * @param id объекта.
     * @return true - если удаление проведено успешно.
     *         false - если удаление не удалось.
     */
    @Override
    public boolean delete(String id) {
        int objectIndex = findObjectIndexById(id);
        if (objectIndex == -1) {
            return false;
        }
        simpleArray.delete(objectIndex);
        return true;
    }

    /**
     * Возвращает объект по id.
     * @param id объекта
     * @return объект с заданным id.
     */
    @Override
    public T findById(String id) {
        int objectIndex = findObjectIndexById(id);
        if (objectIndex == -1) {
            throw new NoSuchElementException();
        }
        return simpleArray.get(objectIndex);
    }
}
