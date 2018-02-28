package ru.job4j.professions;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test класса Teacher.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class TeacherTest {
    /**
     * Test teach.
     */
    @Test
    public void ifTeacherTeachStudenThenReturnStringTeacherOlegTeachStudentIvan() {
        Teacher oleg = new Teacher("Oleg");
        Student ivan = new Student("Ivan");
        assertThat(oleg.teach(ivan), is("Teacher Oleg teach student Ivan"));
    }
}
