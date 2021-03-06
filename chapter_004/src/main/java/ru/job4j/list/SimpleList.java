package ru.job4j.list;

/**
 * Интерфейс, для работы со списками данных,
 * наследует интерфейс Iterable<E>
 * @param <E>
 */
public interface SimpleList<E> extends Iterable<E> {
    /**
     * В классе, который реализует данный интерфейс,
     * метод должен добавить объект в хранилище
     * @param value объект, который необходимо добавить в хранилище.
     */
    void add(E value);

    /**
     * В классе, который реализует данный интерфейс,
     * метод должен вернуть ссылку на объект с заданным индексом из хранилища.
     * @param index заданный индекс.
     * @return ссылка на требуемый объект.
     */
    E get(int index);
}