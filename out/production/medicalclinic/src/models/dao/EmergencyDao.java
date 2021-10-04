package models.dao;

import models.beans.Emergency;
import models.beans.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmergencyDao {

    public List<Emergency> getEmergencies() throws ClassNotFoundException {
        List<Emergency> emergencies = new ArrayList<Emergency>();
        String SELECT_Emergencies_SQL = "SELECT * from emergencies" ;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/medical_clinic?useSSL=false", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_Emergencies_SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Emergency emergency = new Emergency();

                Integer id  = result.getInt("id");
                String g = result.getString("name");

                emergency.setId(id);
                emergency.setName(g);

                emergencies.add(emergency);
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return emergencies;
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
