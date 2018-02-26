package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test ArrayChar
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArrayChar2Test {

    /**
     * Test contains если слово word содержится в слове origin.
     */
    @Test
    public void whenOriginWordContainsWordThenTrue() {
        ArrayChar2 word = new ArrayChar2();
        boolean result = word.contains("Сумасшествие", "шест");
        assertThat(result, is(true));
    }

    /**
     * Test contains если слово word не содержится в слове origin.
     */
    @Test
    public void whenOriginWordNotContainsWordThenFalse() {
        ArrayChar2 word = new ArrayChar2();
        boolean result = word.contains("Проверка", "Прок");
        assertThat(result, is(false));
    }
}
