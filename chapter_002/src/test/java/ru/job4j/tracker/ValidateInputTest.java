package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ValidateInputTest {
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    /**
     * Перенаправляем поток исходящих данных в mem.
     */
    @Before
    public void loadMem() {
        System.setOut(new PrintStream(this.mem));
    }

    /**
     * Возвращаем станданртный поток исходящих данных.
     */
    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    /**
     * Test invalid ValidateInput.
     */
    @Test
    public void whenInvalidInput() {
        ValidateInput input = new ValidateInput(
                new StubInput(new String[] {"invalid", "1"})
        );
        input.ask("Enter", new ArrayList<>(Collections.singleton(1)));
        assertThat(
                this.mem.toString(),
                is(
                        String.format("Please, enter validate data again.%n")
                )
        );
    }
}
