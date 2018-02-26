package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test ArrayDuplicate
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicateTest {
    /**
     * Test remove.
     */
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        String[] array = {"Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] except = {"Привет", "Супер", "Мир"};
        ArrayDuplicate duplicate = new ArrayDuplicate();
        String[] result = duplicate.remove(array);
        assertThat(result, is(except));
    }

}
