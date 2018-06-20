package ru.job4j.xmlsxtljdbc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Трансформирует данные в XML.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 10.06.2018
 */
public class StoreXML {
    /**
     * Целевой файл.
     */
    private final File target;

    /**
     * Инициализирует целевой файл.
     * @param target целевой файл.
     */
    StoreXML(File target) {
        this.target = target;
    }

    /**
     * Трансформирует данные из входящего списка
     * в XML и записывает в целевой файл.
     * @param list список объектов Entry.
     */
    void save(List<Entry> list) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Entries.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(new Entries(list), this.target);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * JavaBean.
     * Описывает объект Entry.
     */
    @XmlRootElement
    public static class Entry {
        private int field;

        public Entry() {
        }

        public Entry(int field) {
            this.field = field;
        }

        public int getField() {
            return this.field;
        }

        public void setField(int field) {
            this.field = field;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry entry = (Entry) o;
            return Objects.equals(field, entry.field);
        }

        @Override
        public int hashCode() {
            return Objects.hash(field);
        }
    }

    /**
     * JavaBean.
     * Описывает объект Entries.
     */
    @XmlRootElement
    public static class Entries {
        List<Entry> entry;

        public Entries() {
        }

        public Entries(List<Entry> entry) {
            this.entry = entry;
        }

        public void setEntry(List<Entry> entry) {
            this.entry = entry;
        }

        public List<Entry> getEntry() {
            return this.entry;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entries entries = (Entries) o;
            return Objects.equals(entry, entries.entry);
        }

        @Override
        public int hashCode() {
            return Objects.hash(entry);
        }
    }
}
