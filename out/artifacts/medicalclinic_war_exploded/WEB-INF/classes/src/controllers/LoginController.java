package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.beans.Patient;
import models.beans.User;
import models.services.PatientService;
import models.services.UserService;


@WebServlet("/login")
public class LoginController extends HttpServlet{
    private UserService userService;
    private PatientService patientService;

    public void init() {

        userService = new UserService();
        patientService = new PatientService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String last_name = request.getParameter("last_name");
        String password = request.getParameter("password");
        int role = Integer.parseInt(request.getParameter("role"));
        User user = new User();
        user.setName(name);
        user.setLastName(last_name);
        user.setPassword(password);
        user.setRole(role);

        Patient patient = new Patient();
        patient.setName(name);
        patient.setLast_name(last_name);

        try {
            if ((role != 5 && userService.validateUser(user)) || (role==5 && patientService.validatePatient(patient))) {
                HttpSession session = request.getSession();
                 session.setAttribute("name",name);
                 session.setAttribute("last_name",last_name);
                 session.setAttribute("role",role);
                response.sendRedirect(request.getContextPath()+"/afterLogin");
            } else {
                response.sendRedirect(request.getContextPath());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
