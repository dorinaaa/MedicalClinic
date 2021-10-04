package controllers;

import models.beans.Medicament;
import models.services.MedicamentService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateMedicament")
public class MedicamentController extends HttpServlet {
    Medicament medicament;
    MedicamentService medicamentService;

    public  void init() {
        medicament = new Medicament();
        medicamentService = new MedicamentService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int av = Integer.parseInt(request.getParameter("av"));
        int id = Integer.parseInt(request.getParameter("id"));
        medicament.setId(id);
        try {
            medicamentService.changeMedicamentAvailability(medicament, av);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath()+"/afterLogin");
    }
}
