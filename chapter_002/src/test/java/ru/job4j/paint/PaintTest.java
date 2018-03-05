package ru.job4j.paint;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test Paint.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class PaintTest {

    /**
     * Test drow Square.
     */
    @Test
    public void whenDrawSquare() {
        PrintStream stdout = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new Paint().draw(new Square());
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("†††††††††")
                                .append("†     †")
                                .append("†     †")
                                .append("†††††††††\r\n")
                                .toString()
                )
        );
        System.setOut(stdout);
    }

    /**
     * Test drow Triangle.
     */
    @Test
    public void whenDrawTriangle() {
        PrintStream stdout = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        new Paint().draw(new Triangle());
        assertThat(
                new String(out.toByteArray()),
                is(
                        new StringBuilder()
                                .append("   †   ")
                                .append("  †††  ")
                                .append(" †††††† ")
                                .append("†††††††††\r\n")
                                .toString()
                )
        );
        System.setOut(stdout);
    }
}
