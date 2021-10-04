package models.beans;


import java.sql.Date;

public class Appointment {
    private int id;
    private Patient patient;
    private User doctor;
    private String diagnosis;
    private Date scheduled_at;
    private Medicament medicament;

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Date getScheduled_at() {
        return scheduled_at;
    }

    public void setScheduled_at(Date scheduled_at) {
        this.scheduled_at = scheduled_at;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getNext_appointment() {
        return next_appointment;
    }

    public void setNext_appointment(Date next_appointment) {
        this.next_appointment = next_appointment;
    }

    private Date next_appointment;

}
