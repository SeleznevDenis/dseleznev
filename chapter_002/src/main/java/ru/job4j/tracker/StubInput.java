package ru.job4j.tracker;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StubInput implements Input {

    private final String[] value;

    /**
     * position по умолчаю инициализируется нулём.
     */
    private int position;

    /**
     * Конструктор, инициализирует поле value.
     * @param value
     */
    public StubInput(final String[] value) {
        this.value = value;
    }

    /**
     * Переопределенный метод ask, интерфейса Input.
     * Каждый раз когда его вызывают, возвращает строку из массива value
     * из ячейки соответствующей положению указателя position по умолчанию
     * проинициализированной нулём, после этого указатель сдвигается на +1.
     * @param question может быть любым, для данного метода значения не имеет.
     * @return строку из массива value.
     */
    @Override
    public String ask(String question) {
        return this.value[this.position++];
    }

    /**
     * Перегруженый метод ask для ввода и проверки ввода.
     * @param question  в данном методе не используется.
     * @param range массив допустимых для ввода значений.
     * @return целое число из массива value.
     */
    public int ask(String question, int[] range) {
            return Integer.valueOf(this.ask(question));
    }
}