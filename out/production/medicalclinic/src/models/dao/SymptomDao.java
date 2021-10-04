package models.dao;

import models.beans.Emergency;
import models.beans.Patient;
import models.beans.Symptom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SymptomDao {

    public List<Symptom> getSymptoms(Patient patient) throws ClassNotFoundException {
        List<Symptom> symptoms = new ArrayList<Symptom>();
        String SELECT_Symptoms_SQL = "";
        if (patient == null) {
            SELECT_Symptoms_SQL ="SELECT * from symptoms s" ;
        } else
            SELECT_Symptoms_SQL ="SELECT * from patients_symptoms p inner join symptoms s " +
                    " on p.symptom_id = s.id  where p.patient_id = "+patient.getP_id() ;


        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_Symptoms_SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){

                Symptom symptom = new Symptom();

                Integer id  = result.getInt("s.id");
                String g = result.getString("s.name");

                symptom.setId(id);
                symptom.setName(g);

                symptoms.add(symptom);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return symptoms;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
