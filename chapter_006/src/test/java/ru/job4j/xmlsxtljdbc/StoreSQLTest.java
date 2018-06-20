package ru.job4j.xmlsxtljdbc;

import org.junit.Test;
import ru.job4j.xmlsxtljdbc.StoreSQL;
import ru.job4j.xmlsxtljdbc.StoreXML;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test StoreSQL.
 * @author Denis Seleznev
 * @version $Id$
 * @since 10.06.2018
 */
public class StoreSQLTest {

    @Test
    public void ifGenerate10ElementsThenGetDataReturnThem() {
        Properties connectionProperty = new Properties();
        try (InputStream inStream = getClass().getClassLoader().getResourceAsStream("config.properties");
             StoreSQL testStore = new StoreSQL(connectionProperty)) {
            connectionProperty.load(inStream);
            testStore.init();
            int numberOfFields = 100;
            testStore.generate(numberOfFields);
            List<StoreXML.Entry> expect = new ArrayList<>();
            for (int i = 0; i < numberOfFields; i++) {
                expect.add(new StoreXML.Entry(i));
            }
            assertThat(testStore.getData(), is(expect));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}