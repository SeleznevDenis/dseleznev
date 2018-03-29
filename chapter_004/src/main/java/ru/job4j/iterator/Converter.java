package ru.job4j.iterator;

import java.util.Iterator;

/**
 * Итератор итераторов.
 * @author Denis Seleznev
 * @version $Id$
 * @since 29.03.2018
 */
public class Converter {

    /**
     * Метод конвертирует итератор с вложенными итераторами в итератор чисел.
     * @param it - итератор с вложенными итераторами.
     * @return итератор чисел.
     */
    public Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {

            private Iterator<Integer> currentNestedIt = initialCurrentNestedIt();

            /**
             * Инициализирует поле currentNestedIt.
             * @return первый непустой вложенный итератор, либо, если все
             * вложенные итераторы пусты - последним вложенным итератором.
             */
            private Iterator<Integer> initialCurrentNestedIt() {
                Iterator<Integer> currentNestedIt = it.next();
                while (!currentNestedIt.hasNext() && it.hasNext()) {
                    currentNestedIt = it.next();
                }
                return currentNestedIt;
            }

            /**
             * Проверяет наличие следующего элемента.
             * @return true - если элемент есть,
             *         false - если элементов больше нет.
             */
            @Override
            public boolean hasNext() {
                return (this.currentNestedIt.hasNext() || it.hasNext());
            }

            /**
             * Возвращает число и сдвигает указатель, обходя все вложенные итераторы.
             * @return текущее число под указателем.
             */
            @Override
            public Integer next() {
                Integer result;
                if (this.currentNestedIt.hasNext()) {
                    result = this.currentNestedIt.next();
                } else {
                    this.currentNestedIt = it.next();
                    result = this.currentNestedIt.next();
                }
                return result;
            }
        };
    }
}
