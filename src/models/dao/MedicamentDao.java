package models.dao;

import models.beans.Medicament;
import models.beans.Medicament;
import models.beans.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDao {

    public List<Medicament> selectMedicament(Patient patient, int availability, Date best_b) throws ClassNotFoundException {
        List<Medicament> medicaments = new ArrayList<Medicament>();
        String SELECT_Medicaments_SQL = "";
        if (availability == 0 && best_b ==null && patient == null) {
            SELECT_Medicaments_SQL = "SELECT * from medicaments m" ;
        } else if (availability == 0 && patient == null) {
            SELECT_Medicaments_SQL = "SELECT * from medicaments m where best_before = "+best_b ;
        } else if (best_b == null && patient == null){
            SELECT_Medicaments_SQL = "SELECT * from medicaments m where availability = "+availability ;
        } else {
            SELECT_Medicaments_SQL = "select * from medicaments m inner join appointment_medicament am on am.med_id = m.id " +
                    " inner join appointments a on a.id = am.app_id where a.patient_id = " +patient.getP_id();
        }

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_Medicaments_SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){

                Medicament medicament = new Medicament();

                Integer id  = result.getInt("m.id");
                String g = result.getString("m.name");
                Integer av = result.getInt("m.availability");
                Float price = result.getFloat("m.price");
                Date best_before = result.getDate("m.best_before");

                medicament.setId(id);
                medicament.setName(g);
                medicament.setAvailability(av);
                medicament.setPrice(price);
                medicament.setBest_before(best_before);

                medicaments.add(medicament);
            }
        System.out.println(preparedStatement);
        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return medicaments;
    }

    public int updateMedicament(Medicament medicament, int availability) throws ClassNotFoundException {
        String Update_SQL = "Update medicaments set availability =  "
                + availability +" where id = " +medicament.getId();

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(Update_SQL)) {
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
