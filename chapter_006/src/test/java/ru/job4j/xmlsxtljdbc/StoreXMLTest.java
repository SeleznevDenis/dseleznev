package ru.job4j.xmlsxtljdbc;

import org.junit.Test;
import ru.job4j.xmlsxtljdbc.StoreXML;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test StoreXML.
 * @author Denis Seleznev
 * @version $Id$
 * @since 10.06.2018
 */
public class StoreXMLTest {

    @Test
    public void testSave() {
        List<StoreXML.Entry> inputData = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            inputData.add(new StoreXML.Entry(i));
        }
        File target = new File(getClass().getClassLoader().getResource("StoreXMLTest.xml").getFile());
        StoreXML testStore = new StoreXML(target);
        testStore.save(inputData);
        StringBuilder result = new StringBuilder();
        try (Scanner scanner = new Scanner(target)) {
            while (scanner.hasNext()) {
                result.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder expect = new StringBuilder();
        expect
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .append("<entries>")
                .append("    <entry>")
                .append("        <field>0</field>")
                .append("    </entry>")
                .append("    <entry>")
                .append("        <field>1</field>")
                .append("    </entry>")
                .append("</entries>");
        assertThat(result.toString(), is(expect.toString()));
    }
}