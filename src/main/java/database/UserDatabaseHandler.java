package database;

import model.User;

import java.sql.*;


public class UserDatabaseHandler {

    public static void addUser(User user) {

        Connection connection = DatabaseConnector.getConnection();

        if (connection !=null) {
            System.out.println("Connection Successful");

            try {

                String query = " insert into user (name, email, username, password, address) values (?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                /*
                preparedStmt.setString(1, name);
                preparedStmt.setString(2, email);
                preparedStmt.setString(3, username);
                preparedStmt.setString(4, password;
                preparedStmt.setString(5, address);

                // execute the preparedStatement
                preparedStmt.execute();
                */

                ResultSet rs2 = preparedStmt.executeQuery(query);

                while (rs2.next()) {
                    String name = rs2.getString("name");
                    String email = rs2.getString("email");
                    String username = rs2.getString("username");
                    String address = rs2.getString("address");
                    System.out.println(name + " | " + email + " | " + username + " | " + address);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else {
            System.out.println("Connection Fails");
        }
    }


    /*
    public void displayUsers(String username, String password) {
        try {
            String queryString = "SELECT * FROM user where username =? and password=?";

            //set this values using PreparedStatement
            PreparedStatement stmt = null;
            ResultSet results = stmt.executeQuery(queryString); //where ps is Object of PreparedStatement



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
}
