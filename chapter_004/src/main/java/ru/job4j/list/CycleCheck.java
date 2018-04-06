package ru.job4j.list;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 02.04.2018
 */
public class CycleCheck {

    /**
     * Проверяет наличие замыканий в однонаправленном
     * связном списке. Используется алгоритм Флойда.
     * @param first первый узел списка.
     * @param <T> параметризованный тип.
     * @return true - если список содержит замыкания.
     *         false - если список не содержит замыканий.
     */
    public <T> boolean hasCycle(Node<T> first) {
        boolean result = false;
        if (first != null) {
            Node<T> slow = first;
            Node<T> fast = first;
            while (!result && slow.getNext() != null
                    && fast.getNext() != null && fast.getNext().getNext() != null) {
                slow = slow.getNext();
                fast = fast.getNext().getNext();
                if (slow.equals(fast)) {
                    result = true;
                }
            }
        }
        return result;
    }
}
