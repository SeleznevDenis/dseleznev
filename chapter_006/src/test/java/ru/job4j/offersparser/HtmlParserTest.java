package ru.job4j.offersparser;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test HtmlParser.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class HtmlParserTest {

    @Test
    public void testParser() {
        HtmlParser parser = new HtmlParser();
        Set<Vacancy> result = null;
        try {
            parser.init();
            Calendar stopDate = new GregorianCalendar(2018, 0, 1);
            parser.setStopDate(stopDate);
            result = parser.parseFile(
                    new File(getClass().getClassLoader().getResource("ParserTestHtml.html").getFile())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Calendar firstExpectVacancyDate = new GregorianCalendar(2018, 5, 18, 22, 7, 0);
        Calendar secondExpectVacancyDate = new GregorianCalendar(2018, 5, 11, 0, 3, 0);
        Set<Vacancy> expect = new HashSet<>();
        expect.add(new Vacancy("java vacancy 1 [new]", "http://java-vacancy-1.ru", firstExpectVacancyDate));
        expect.add(new Vacancy("Java, vacancy [new]", "http://java-vacancy.ru", secondExpectVacancyDate));
        assertThat(result, is(expect));
    }
}