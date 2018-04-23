package ru.job4j.map;

import java.util.HashMap;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 21.04.2018
 */
public class Anagrams {
    /**
     * Проверяет, являются ли заданные слова анаграммами.
     *
     * @param first первое заданное слово.
     * @param second второе заданное слово.
     * @return true, если заданные слова являются анаграммами.
     *         false, если заданные слова не являются анаграммами.
     */
    public boolean checkWords(String first, String second) {
        boolean result = true;
        if (first.length() != second.length()) {
            result = false;
        } else {
            HashMap<Character, Integer> symbolStorage = new HashMap<>();
            for (Character symbol : first.toLowerCase().toCharArray()) {
                Integer symbolCounter = symbolStorage.putIfAbsent(symbol, 1);
                if (symbolCounter != null) {
                    symbolStorage.put(symbol, symbolCounter + 1);
                }
            }
            for (Character symbol : second.toLowerCase().toCharArray()) {
                Integer currentValue = symbolStorage.get(symbol);
                if (currentValue == null) {
                    result = false;
                    break;
                }
                if (currentValue > 1) {
                    symbolStorage.replace(symbol, currentValue - 1);
                } else {
                    symbolStorage.remove(symbol);
                }
            }
        }
        return result;
    }
}
