package models.dao;

import models.beans.Gender;
import models.beans.Patient;
import models.beans.Symptom;

import java.sql.*;
import java.util.Date;

public class PatientDao {

    public int insertPatient(Patient patient) throws ClassNotFoundException {
        String INSERT_PATIENT_SQL = "INSERT INTO patients " +
                "  (name, last_name, gender_id, age, emergency_id, registration_id, created_at) VALUES " +
                " ( ?, ?, ?, ?, ?,?,now());";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PATIENT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setString(2, patient.getLast_name());
            preparedStatement.setInt(3, patient.getGender().getId());
            preparedStatement.setInt(4, patient.getAge());
            preparedStatement.setInt(5, patient.getEmergency().getId());
            preparedStatement.setString(6,  patient.getRegistration_id());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                patient.setP_id(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }

    public int insertPatientSymptom(Patient patient, Symptom symptom) throws ClassNotFoundException {
        String INSERT_PATIENT_SYMPTOM_SQL = "INSERT INTO patients_symptoms " +
                "  (patient_id, symptom_id) VALUES " +
                " ( ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PATIENT_SYMPTOM_SQL)) {
            preparedStatement.setInt(1, patient.getP_id());
            preparedStatement.setInt(2, symptom.getId());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }

    public Patient selectPatient(String name, String last_name) throws ClassNotFoundException {
        Patient patient = null;
        String SELECT_GENDERS_SQL = "SELECT * from patients  where " +
                " name = '"+name+"' and last_name = '"+last_name+ "' limit 1";

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GENDERS_SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){

                 patient = new Patient();
                Integer id  = result.getInt("patients.id");
                patient.setP_id(id);

            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return patient;
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
