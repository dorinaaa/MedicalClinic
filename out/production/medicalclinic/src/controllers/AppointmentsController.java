package controllers;

import models.beans.*;
import models.beans.Gender;
import models.services.AppointmentService;
import models.services.PatientService;
import models.services.TransactionService;
import models.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@WebServlet("/registerAppointment")
public class AppointmentsController extends HttpServlet {
    private PatientService patientService;
    private UserService userService;
    private Patient patient;
    private Gender gender;
    private Emergency emergency;
    private List<Symptom> symptoms;
    private Appointment appointment;
    private AppointmentService appointmentService;
    private Transaction transaction;
    private TransactionService transactionService;


    public void init() {

        patientService = new PatientService();
        userService = new UserService();
        symptoms = new ArrayList<Symptom>();
        patient = new Patient();
        gender = new Gender();
        emergency = new Emergency();
        appointment = new Appointment();
        appointmentService = new AppointmentService();
        transaction = new Transaction();
        transactionService = new TransactionService();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String last_name = request.getParameter("last_name");
        int gender_id = Integer.parseInt(request.getParameter("gender"));
        int age = Integer.parseInt(request.getParameter("age"));
        int emergency_id = Integer.parseInt(request.getParameter("emergency"));
        int s1 = Integer.parseInt(request.getParameter("s1"));
        int s2 = Integer.parseInt(request.getParameter("s2"));
        int s3 = Integer.parseInt(request.getParameter("s3"));
        String price = request.getParameter("price");

        gender.setId(gender_id);
        emergency.setId(emergency_id);

        createSymptoms(s1, s2, s3);

        CreatePatient(age, name, last_name);

        CreateAppointment();

        registerTransaction(price);

        HttpSession session = request.getSession();
        session.setAttribute("appointment",appointment);
        session.setAttribute("role",1);
        response.sendRedirect(request.getContextPath()+"/afterLogin");
    }

    private void registerTransaction(String price) {
        transaction.setPatient(patient);
        transaction.setRole(1);
        transaction.setPrice(Float.parseFloat(price));
        transaction.setTransaction_type("Registration fee");
        try {
            transactionService.registerTransaction(transaction);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void CreateAppointment() {
        appointment.setPatient(patient);
        try {
            scheduleAppointment(appointment);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void CreatePatient(int age, String name, String last_name) {
        patient.setName(name);
        patient.setLast_name(last_name);
        patient.setGender(gender);
        patient.setAge(age);
        patient.setEmergency(emergency);
        patient.setRegistration_id(generateString());
        patient.setSymptoms(symptoms);
        try {
            patientService.registerPatient(patient);
            patientService.registerPatientSymptoms(patient, symptoms);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void scheduleAppointment(Appointment appointment) throws ClassNotFoundException {
            User doctor = userService.getFreeDoctor();
            appointment.setDoctor(doctor);
            appointment.setScheduled_at(new Date(new java.util.Date().getTime()));
            appointmentService.registerAppointment(appointment);
    }

    private void createSymptoms(int s1, int s2, int s3){
        Symptom sym1 = new Symptom();
        sym1.setId(s1);
        Symptom sym2 = new Symptom();
        sym2.setId(s2);
        Symptom sym3 = new Symptom();
        sym3.setId(s3);

        symptoms.add(sym1);
        symptoms.add(sym2);
        symptoms.add(sym3);
    }

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return  uuid;
    }
}
