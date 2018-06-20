package ru.job4j.xmlsxtljdbc;

import org.junit.Test;
import ru.job4j.xmlsxtljdbc.ConvertXSQT;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test ConvertXSQT
 * @author Denis Seleznev
 * @version $Id$
 * @since 10.06.2018
 */
public class ConvertXSQTTest {

    @Test
    public void testConvert() {
        ConvertXSQT converter = new ConvertXSQT();
        File source = new File(getClass().getClassLoader().getResource("ConvertXSQTtestSource.xml").getFile());
        File dest = new File(getClass().getClassLoader().getResource("ConvertXSQTtestDest.xml").getFile());
        File scheme = new File(getClass().getClassLoader().getResource("scheme.xsl").getFile());
        converter.convert(source, dest, scheme);
        StringBuilder result = new StringBuilder();
        try (Scanner scan = new Scanner(dest)) {
            while (scan.hasNext()) {
                result.append(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String expect = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><entries>"
                      + "    <entry field=\"0\"/>"
                      + "    <entry field=\"1\"/>"
                      + "</entries>";
        assertThat(result.toString(), is(expect));
    }
}