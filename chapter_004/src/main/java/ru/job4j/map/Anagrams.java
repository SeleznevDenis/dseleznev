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
            for (Character symbol : first.toCharArray()) {
                int symbolCounter = 1;
                if (symbolStorage.containsKey(symbol)) {
                    symbolCounter = symbolStorage.get(symbol) + 1;
                }
                symbolStorage.put(symbol, symbolCounter);
            }
            for (Character symbol : second.toCharArray()) {
                if (symbolStorage.containsKey(symbol)) {
                    int currentValue = symbolStorage.get(symbol);
                    if (currentValue > 1) {
                        symbolStorage.put(symbol, currentValue - 1);
                    } else {
                        symbolStorage.remove(symbol);
                    }
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
