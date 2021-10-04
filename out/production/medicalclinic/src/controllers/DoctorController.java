package controllers;

import models.beans.Appointment;
import models.beans.Medicament;
import models.services.AppointmentService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/updateAppointment")
public class DoctorController extends HttpServlet {
    private Appointment appointment;
    private AppointmentService appointmentService;
    private List<Medicament> medicaments;
    private Medicament medicament;

    public void init() {
            appointment = new Appointment();
            appointmentService = new AppointmentService();
            medicament = new Medicament();
            medicaments = new ArrayList<Medicament>();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int appointment_id = Integer.parseInt(request.getParameter("id"));
        String diagnosis = request.getParameter("diagnosis");
//        Date next_app = Date.valueOf(request.getParameter("next_app"));
        int medicament_id = Integer.parseInt(request.getParameter("medicament"));

        appointment.setId(appointment_id);
        try {
            appointmentService.setAppointmentDiagnosis(appointment, diagnosis);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        if (next_app != null) {
//            try {
//                appointmentService.setNextAppointment(appointment, next_app);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
        medicament.setId(medicament_id);
        medicaments.add(medicament);
        try {
            appointmentService.setMedicaments(appointment, medicaments);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath()+"/afterLogin");
    }

}
