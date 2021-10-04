package models.dao;

import models.beans.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenderDao {

    public List<Gender> getGenders() throws ClassNotFoundException {
        List<Gender> genders = new ArrayList<Gender>();
        String SELECT_GENDERS_SQL = "SELECT * from genders" ;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GENDERS_SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){

                Gender gender = new Gender();

                Integer id  = result.getInt("id");
                String g = result.getString("gender");

                gender.setId(id);
                gender.setGender(g);

                genders.add(gender);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return genders;
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
