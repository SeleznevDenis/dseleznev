package ru.job4j.carplace.persistens.interfaces;

import java.io.Serializable;

/**
 * Интерфейс маркер, для объектов которые можно использовать в UserDao.
 * Так-же объекты обязаны содержать метод getId.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public interface IEntity extends Serializable {
    public int getId();
}
