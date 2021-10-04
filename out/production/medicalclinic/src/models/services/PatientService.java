package models.services;

import models.beans.Patient;
import models.beans.Symptom;
import models.dao.PatientDao;

import java.util.List;

public class PatientService {

    public void registerPatient(Patient patient) throws ClassNotFoundException {
        PatientDao  patientDao = new PatientDao();
        patientDao.insertPatient(patient);
    }

    public boolean validatePatient(Patient patient) throws ClassNotFoundException {
        PatientDao patientDao = new PatientDao();
        return patientDao.selectPatient(patient.getName(), patient.getLast_name()) != null;
    }

    public void registerPatientSymptoms(Patient patient, List<Symptom> symptoms) throws ClassNotFoundException {
        PatientDao  patientDao = new PatientDao();
        for (Symptom symptom: symptoms) {
            patientDao.insertPatientSymptom(patient, symptom);
        }
    }

    public Patient getPatient(String name, String last_name) throws ClassNotFoundException {
        PatientDao patientDao = new PatientDao();
        return patientDao.selectPatient(name, last_name);
    }
}
