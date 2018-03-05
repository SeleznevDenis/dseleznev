package ru.job4j.paint;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test Triangle.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class TriangleTest {

    /**
     * Test draw.
     */
    @Test
    public void whenDrawSquare() {
        Triangle triangle = new Triangle();
        assertThat(
                triangle.draw(),
                is(
                        new StringBuilder()
                            .append("   †   ")
                            .append("  †††  ")
                            .append(" †††††† ")
                            .append("†††††††††")
                            .toString()
                )
        );
    }
}
