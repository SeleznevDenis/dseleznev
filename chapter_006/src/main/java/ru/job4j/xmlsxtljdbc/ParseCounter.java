package ru.job4j.xmlsxtljdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Attributes counter.
 * @author Denis Seleznev
 * @version $Id$
 * @since 10.06.2018
 */
public class ParseCounter {

    private final static Logger LOGGER = LoggerFactory.getLogger(StoreSQL.class);
    /**
     * Счетчик.
     */
    private int counter = 0;

    /**
     * Суммирует значения атрибутов field из исходного файла и выводит в консоль.
     * @param file исходный файл.
     * @return сумма значений атрибутов.
     */
    public int getSum(File file) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(file, new Parser());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        LOGGER.info("this.counter");
        return this.counter;
    }

    /**
     * SAX парсер
     */
    private class Parser extends DefaultHandler {
        /**
         * Проходит по всем аттрибутам, и если имя атрибута равно field,
         * то прибавляет значение этого аттрибута к счетчику.
         */
        @Override
        public void startElement(
                String namespaceUri, String localName, String qName, Attributes atts) {
            int attributeLength = atts.getLength();
            for (int i = 0; i < attributeLength; i++) {
                String attributeName = atts.getQName(i);
                if (attributeName != null && attributeName.equals("field") && atts.getValue(i) != null) {
                    counter += Integer.parseInt(atts.getValue(i));
                }
            }
        }
    }
}
