package xmlsxtljdbc;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test ParseCounter.
 * @author Denis Seleznev
 * @version $Id$
 * @since 10.06.2018
 */
public class ParseCounterTest {

    @Test
    public void getSum() {
        ParseCounter testCounter = new ParseCounter();
        int result = testCounter.getSum(
                new File(getClass().getClassLoader().getResource("ParseCounterTestSource.xml").getFile())
        );
        assertThat(result, is(28));
    }
}