package models.dao;

import models.beans.Appointment;
import models.beans.Patient;
import models.beans.Transaction;
import models.beans.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    public int insertTransaction(Transaction app) throws ClassNotFoundException {

        String INSERT_SQL = "INSERT INTO transactions " +
                "  (patient_id, role_id, transaction_type, created_at, price) VALUES " +
                " ( ?, ?, ?, now(), ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
            preparedStatement.setInt(1, app.getPatient().getP_id());
            preparedStatement.setInt(2, app.getRole());
            preparedStatement.setString(3,  app.getTransaction_type());
            preparedStatement.setFloat(4, app.getPrice());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }

    public List<Transaction> selectTransactions(int role, Patient patient) throws ClassNotFoundException {
        List<Transaction> transactions = new ArrayList<Transaction>();
        String SELECT_Emergencies_SQL = "";
        if (role == 0 && patient== null) {
            SELECT_Emergencies_SQL = "SELECT * from transactions a inner join patients p " +
                    "on a.patient_id = p.id " ;
        } else if (role !=0) {
            SELECT_Emergencies_SQL = "SELECT * from transactions a inner join patients p " +
                    "on a.patient_id = p.id  where a.role_id = "+role;
        } else {
            SELECT_Emergencies_SQL = "SELECT * from transactions a inner join patients p " +
                    "on a.patient_id = p.id  where a.patient_id = "+patient.getP_id();
        }


        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_Emergencies_SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Transaction app = new Transaction();

                String patient_name  = result.getString("p.name");
                Integer role_id  = result.getInt("a.role_id");
                String trans_type = result.getString("a.transaction_type");
                Float price = result.getFloat("a.price");
                Date scheduled_at = result.getDate("a.created_at");

                Patient p = new Patient();
                p.setName(patient_name);
                app.setPatient(p);
                app.setRole(role_id);
                app.setTransaction_type(trans_type);
                app.setPrice(price);
                app.setCreated_at(scheduled_at);

                transactions.add(app);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return transactions;
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
