package ru.job4j.professions;

/**
 * Doctor
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Doctor extends Profession {

    /**
     * Конструктор класса Doctor.
     * инициализирует переменные name и profession экземпляра класса.
     * @param name
     */
    public Doctor(String name) {
        super(name, "Doctor");
    }

    /**
     * threat.
     * @param patient объект лечения.
     * @return строка с информацией кто лечит и кого.
     */
    public String threat(Patient patient) {
        return this.profession + " " + this.name + " threat " + patient.name;
    }

    /**
     * heal.
     * @param patient
     * @return диагноз пациента
     */
    public Diagnose heal(Patient patient) {
        return patient.diagnose;
    }

    /**
     * diagnosed - устанавливает диагноз пациенту,
     * длительность болезни и предполагаемое лечение.
     * @param patient
     * @param disease
     * @param duration
     * @param treatment
     */
    public void diagnosed(Patient patient, String disease, int duration, String treatment) {
        patient.diagnose = new Diagnose(disease, duration, treatment);
    }
}
