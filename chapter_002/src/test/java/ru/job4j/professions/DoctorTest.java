package ru.job4j.professions;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test класса доктор
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class DoctorTest {

    Doctor doc = new Doctor("Bob");
    Patient pit = new Patient("Pit");

    /**
     * Test threat.
     */
    @Test
    public void ifDocThreatPitThenReturnDoctorBobThreatPit() {
        String expect = "Doctor Bob threat Pit";
        String result = doc.threat(pit);
        assertThat(result, is(expect));
    }

    /**
     * Test heal.
     */
    @Test
    public void ifDocHealPitThenPitReturnDiagnose() {
        Diagnose diagnose = new Diagnose("plague", 30, "nothing");
        pit.diagnose = diagnose;
        Diagnose result = doc.heal(pit);
        assertThat(result, is(diagnose));
    }

    /**
     * Test diagnosed.
     */
    @Test
    public void ifDocDiagnosedPitThenPitDiagnosWasDiagnosed() {
        doc.diagnosed(pit, "plague", 30, "nothing");
        String result = pit.diagnose.disease + " " + pit.diagnose.duration + " " + pit.diagnose.treatment;
        String expect = "plague 30 nothing";
        assertThat(result, is(expect));
    }
}
