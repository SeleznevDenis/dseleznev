package ru.job4j.array;

/**
 * ArrayChar2
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArrayChar2 {

    /**
     * contains - проверяет есть ли слово word в слове origin
     * @param origin заданное слово в котором производится поиск.
     * @param word заданное слово которое ищем.
     * @return если origin содержит word то true, иначе false.
     */
    public boolean contains(String origin, String word) {
        boolean result = true;
        char[] aOrigin = origin.toCharArray();
        char[] aWord = word.toCharArray();

        for (int i = 0; i <= aOrigin.length - aWord.length; i++) {
            for (int j = 0; j < aWord.length; j++) {
               if (aOrigin[i + j] != aWord[j]) {
                   result = false;
                   break;
               } else {
                   result = true;
               }
            }
            if (result) {
                break;
            }
        }
        return result;
    }
}
