package controllers;

import models.beans.Patient;
import models.beans.User;
import models.dao.EmergencyDao;
import models.dao.GenderDao;
import models.dao.SymptomDao;
import models.services.AppointmentService;
import models.services.MedicamentService;
import models.services.PatientService;
import models.services.TransactionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/afterLogin")
public class RoleController extends HttpServlet {
    private GenderDao genderDao;
    private EmergencyDao emergencyDao;
    private SymptomDao symptomDao;
    private AppointmentService appointmentsService;
    private User doctor;
    private Patient patient;
    private PatientService patientService;
    private MedicamentService medicamentService;
    private TransactionService transactionService;

    public void init() {
         genderDao = new GenderDao();
         emergencyDao = new EmergencyDao();
         symptomDao = new SymptomDao();
         appointmentsService = new AppointmentService();
         doctor = new User();
         patient = new Patient();
         patientService = new PatientService();
         medicamentService = new MedicamentService();
         transactionService = new TransactionService();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int role = (int) session.getAttribute("role");
        switch (role) {
            case 1:
                showReceptionistPage(request, response);
                break;
            case 2:
                showDoctorPage(request, response);
                break;
            case 3:
                showPharmacistPage(request, response);
                break;
            case 4:
                showAccountantPage(request, response);
                break;
            default:
                showPatientPage(request, response, session);
                break;
        }
    }

    private void showPatientPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        String name = (String) session.getAttribute("name");
        String last_name = (String) session.getAttribute("last_name");
        try {
            patient = patientService.getPatient(name, last_name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            request.setAttribute("transactions", transactionService.getPatientTransactions(patient));
            request.setAttribute("appointments", appointmentsService.getPatientAppointments(patient));
            request.setAttribute("medicaments", medicamentService.getPatientsMedicaments(patient));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/patient.jsp");
        dispatcher.forward(request, response);
    }

    private void showAccountantPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("transactions", transactionService.getAllTransactions());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/accountant.jsp");
        dispatcher.forward(request, response);
    }

    private void showPharmacistPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("medicaments", medicamentService.getMedicaments());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/pharmacist.jsp");
        dispatcher.forward(request, response);
    }

    private void showDoctorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doctor.setRole(2);
        try {
            request.setAttribute("appointments", appointmentsService.getDoctorAppointments(doctor));
            request.setAttribute("medicaments", medicamentService.getMedicaments());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/doctor.jsp");
        dispatcher.forward(request, response);
    }

    private void showReceptionistPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("genders", genderDao.getGenders());
            request.setAttribute("emergencies", emergencyDao.getEmergencies());
            request.setAttribute("symptoms", symptomDao.getSymptoms(null));
            request.setAttribute("appointments", appointmentsService.getAllAppointments());
            request.setAttribute("transactions", transactionService.getUserTransactions(1));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/receptionist.jsp");
        dispatcher.forward(request, response);
    }
}
