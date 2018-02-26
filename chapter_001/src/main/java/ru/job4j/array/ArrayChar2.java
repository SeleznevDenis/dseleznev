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
        boolean done = true;
        char[] aOrigin = origin.toCharArray();
        char[] aWord = word.toCharArray();
        for (int aOriginIndex = 0; aOriginIndex <= aOrigin.length - aWord.length; aOriginIndex++) {
            for (int aWordIndex = 0; aWordIndex < aWord.length; aWordIndex++) {
               if (aOrigin[aOriginIndex + aWordIndex] != aWord[aWordIndex]) {
                   done = false;
                   break;
               } else {
                   done = true;
               }
            }
            if (done) {
                break;
            }
        }
        return done;
    }
}
