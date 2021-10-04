package models.dao;

import models.beans.Employee;
import models.beans.Patient;
import models.beans.User;

import java.sql.*;

public class UserDao {

    public boolean selectUser(User user) throws ClassNotFoundException {
        boolean status = false;

        String SELECT_USERS_SQL = "SELECT * from users where name = ? and last_name = ? and password = ? and role_id = ?" ;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getRole());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet result = preparedStatement.executeQuery();
            status = result.next();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return status;
    }

    public User selectDoctorsWithoutAppointments() throws ClassNotFoundException {
        User user = null;
        String SELECT_USERS_SQL = "select * from users where role_id=2 and id not in (select doctor_id from appointments where scheduled_at" +
                " between current_time and '18:00:00')" ;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_SQL)) {

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                user = new User();
                user.setId(result.getInt(1));
                user.setName(result.getString(2));
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return user;
    }

    public int insertUser(User user) throws ClassNotFoundException {
        String INSERT_USER_SQL = "INSERT INTO users " +
                "  (name , last_name, password, role_id, created_at) VALUES " +
                " ( ?, ?, ?, ?, now());";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getRole());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }

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
