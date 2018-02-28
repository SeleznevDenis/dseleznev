package ru.job4j.professions;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test класса Engineer.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class EngineerTest {
    /**
     * Test build.
     */
    @Test
    public void ifEngineerBuildHouseThenReturnEngineerBillBuildHOuseAdres() {
        Engineer bill = new Engineer("Bill");
        House home = new House("Moscovscaya 34");
        assertThat(bill.build(home), is("Engineer Bill build Moscovscaya 34"));
    }
}
