package models.dao;

import models.beans.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {

    public int insertAppointment(Appointment app) throws ClassNotFoundException {
        String INSERT_SQL = "INSERT INTO appointments " +
                "  (patient_id, doctor_id, scheduled_at, diagnosis) VALUES " +
                " ( ?, ?, ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
            preparedStatement.setInt(1, app.getPatient().getP_id());
            preparedStatement.setInt(2, app.getDoctor().getId());
            preparedStatement.setDate(3,  app.getScheduled_at());
            preparedStatement.setString(4, app.getDiagnosis());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }

    public List<Appointment> selectAppointments(User doctor, Patient patient) throws ClassNotFoundException {
        List<Appointment> appointments = new ArrayList<Appointment>();
        String SELECT_Emergencies_SQL = "";
        if (doctor == null && patient== null) {
             SELECT_Emergencies_SQL = "SELECT * from appointments a inner join patients p " +
                    "on a.patient_id = p.id inner join users u on a.doctor_id = u.id " ;
        } else if (doctor !=null) {
             SELECT_Emergencies_SQL = "SELECT * from appointments a inner join patients p " +
                    "on a.patient_id = p.id inner join users u on a.doctor_id = u.id " +
                     "  where a.doctor_id = 2";
        } else {
            SELECT_Emergencies_SQL = "SELECT * from appointments a inner join patients p " +
                    "on a.patient_id = p.id inner join users u on a.doctor_id = u.id " +
                    " where a.patient_id = "+patient.getP_id();
        }


        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_Emergencies_SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Appointment app = new Appointment();
                Integer id = result.getInt("a.id");
                String diagnosis = result.getString("a.diagnosis");
//                Date next_app = result.getDate("a.next_appointment");
//                Integer med_id = result.getInt("m.med_id");
//                Medicament medicament = new Medicament();
//                medicament.setId(med_id);
//                medicament.setName(result.getString("med.name"));
                Integer p_id = result.getInt("p.id");
                String patient_name  = result.getString("p.name");
                String doctor_name  = result.getString("u.name");
                Date scheduled_at = result.getDate("a.scheduled_at");

                SymptomDao symptomDao = new SymptomDao();

                Patient p = new Patient();
                p.setP_id(p_id);
                p.setName(patient_name);
                p.setSymptoms(symptomDao.getSymptoms(p));
                User u_d = new User();
                u_d.setName(doctor_name);
                app.setId(id);
                app.setPatient(p);
                app.setDoctor(u_d);
                app.setScheduled_at(scheduled_at);
                app.setDiagnosis(diagnosis);
//                app.setNext_appointment(next_app);
//                app.setMedicament(medicament);

                appointments.add(app);
            }

            System.out.println(preparedStatement);
        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return appointments;
    }

    public int updateAppointment(Appointment appointment, String diagnosis, Date nextAppointment) throws ClassNotFoundException {
        String UPDATE_SQL = "";
        if (diagnosis != null) {
             UPDATE_SQL = "update appointments set diagnosis = '" + diagnosis +
                     "' where id = "+appointment.getId();
        }
        else if (nextAppointment != null) {
             UPDATE_SQL = "update appointments set  next_appointment = '"
                    +nextAppointment + "' where id = "+appointment.getId();
        }

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }

    public int insertAppointmentMedicament(Appointment appointment, Medicament medicament) throws ClassNotFoundException {
        String INSERT_SQL = "INSERT INTO appointment_medicament " +
                "  (app_id, med_id) VALUES " +
                " ( ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
            preparedStatement.setInt(1, appointment.getId());
            preparedStatement.setInt(2, medicament.getId());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
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
