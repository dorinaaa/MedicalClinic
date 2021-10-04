package models.services;

import models.beans.Appointment;
import models.beans.Medicament;
import models.beans.Patient;
import models.beans.User;
import models.dao.AppointmentDao;

import java.sql.Date;
import java.util.List;

public class AppointmentService {
    public void registerAppointment(Appointment appointment) throws ClassNotFoundException {
        AppointmentDao appointmentDao = new AppointmentDao();
        appointmentDao.insertAppointment(appointment);
    }

    public void setAppointmentDiagnosis(Appointment appointment, String diagnosis) throws ClassNotFoundException {
        AppointmentDao appointmentDao = new AppointmentDao();
        appointmentDao.updateAppointment(appointment, diagnosis, null);
    }

    public void setNextAppointment(Appointment appointment, Date next_appointment) throws ClassNotFoundException {
        AppointmentDao appointmentDao = new AppointmentDao();
        appointmentDao.updateAppointment(appointment, null, next_appointment);
    }

    public void setMedicaments(Appointment appointment, List<Medicament> medicaments) throws ClassNotFoundException {
        AppointmentDao appointmentDao = new AppointmentDao();
        for (Medicament medicament: medicaments) {
            appointmentDao.insertAppointmentMedicament(appointment, medicament);
        }
    }
    public List<Appointment> getAllAppointments() throws ClassNotFoundException {
        AppointmentDao appointmentDao = new AppointmentDao();
        return appointmentDao.selectAppointments(null, null);
    }

    public List<Appointment> getDoctorAppointments(User doctor) throws ClassNotFoundException {
        AppointmentDao appointmentDao = new AppointmentDao();
        return appointmentDao.selectAppointments(doctor, null);
    }

    public List<Appointment> getPatientAppointments(Patient patient) throws ClassNotFoundException {
        AppointmentDao appointmentDao = new AppointmentDao();
        return appointmentDao.selectAppointments(null, patient);
    }
}
