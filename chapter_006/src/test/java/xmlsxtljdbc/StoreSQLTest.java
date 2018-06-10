package xmlsxtljdbc;

import org.junit.Test;

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
        try (InputStream inStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            connectionProperty.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StoreSQL testStore = new StoreSQL(connectionProperty);
        testStore.init();
        int numberOfFields = 100;
        testStore.generate(numberOfFields);
        List<StoreXML.Entry> expect = new ArrayList<>();
        for (int i = 0; i < numberOfFields; i++) {
            expect.add(new StoreXML.Entry(i));
        }
        assertThat(testStore.getData(), is(expect));
    }
}