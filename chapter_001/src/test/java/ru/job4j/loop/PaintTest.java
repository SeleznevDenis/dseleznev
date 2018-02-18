package ru.job4j.loop;

import org.junit.Test;
import java.util.StringJoiner;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Тесты объектов класса Paint.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class PaintTest {
    /**
     * Test тестирует построение правой части пирамиды
     * высотой 4, с выводом рисунка в консоль
     */
    @Test
    public void whenPyramid4Right() {
        Paint paint = new Paint();
        String rst = paint.rightTrl(4);
        System.out.println(rst);
        assertThat(rst,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                        .add("^   ")
                                        .add("^^  ")
                                        .add("^^^ ")
                                        .add("^^^^")
                                        .toString()
                )
        );
    }

    /**
     * Test тестирует построение левой части пирамиды
     * высотой 4, с выводом рисунка в консоль.
     */
    @Test
    public void whenPyramid4Left() {
        Paint paint = new Paint();
        String rst = paint.leftTrl(4);
        System.out.println(rst);
        assertThat(rst,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                 .add("   ^")
                                 .add("  ^^")
                                 .add(" ^^^")
                                 .add("^^^^")
                                 .toString()
                )
        );

    }

    /**
     * Test тестирует построение пирамиды высотой 5
     * с выводом рисунка в консоль.
     */
    @Test
    public void whenPyramidFive() {
        Paint paint = new Paint();
        String rst = paint.pyramid(5);
        System.out.println(rst);
        assertThat(rst,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("    ^    ")
                                .add("   ^^^   ")
                                .add("  ^^^^^  ")
                                .add(" ^^^^^^^ ")
                                .add("^^^^^^^^^")
                                .toString()
                )
        );

    }

}
