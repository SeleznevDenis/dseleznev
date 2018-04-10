package ru.job4j.tree;

import java.util.Optional;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 10.04.2018
 * @param <E> Параметризованный тип, ограничен в пределах классов реализующих Comparable.
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Производит добавление элемента в коллекцию.
     * @param parent корень, к которому требуется добавить элемент наследник.
     * @param child элемент наследник.
     * @return true - если добавление прошло успешно.
     *         false - если добавление не удалось.
     */
    boolean add(E parent, E child);

    /**
     * Производит поиск узла по заданному значению.
     * @param value заданное значение.
     * @return искомый узел.
     */
    Optional<Node<E>> findBy(E value);
}
