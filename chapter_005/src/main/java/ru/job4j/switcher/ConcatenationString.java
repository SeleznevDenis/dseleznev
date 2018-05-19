package ru.job4j.switcher;

/**
 * Строка с конкатенацией числа к строке.
 * @author Denis Seleznev
 * @version $Id$
 * @since 19.05.2018
 */
public class ConcatenationString {
    /**
     * Строка с которой производится конкатенация.
     */
    private String string = "";

    /**
     * Добавляет целое число в конец строки.
     *
     * @param number число для добавления в конец строки.
     */
    public void concatenate(int number) {
        this.string = this.string.concat(String.valueOf(number));
    }

    /**
     * @return Строку с которой производилась конкатенация.
     */
    public String getString() {
        return this.string;
    }
}
