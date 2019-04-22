package database;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDatabaseHandler {

    public User getUserByUsername(String username) throws SQLException{
        Connection connection = DatabaseConnector.getConnection();
        String query = "SELECT * FROM user WHERE username = '" + username + "'";

        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet results = pstmt.executeQuery(query);

        User user = null;
        while (results.next()){
            String password = results.getString("password");
            String name = results.getString("name");
            String email = results.getString("email");
            String user_type = results.getString("user_type");
            user = new User(username, name, email, password, user_type);
        }

        return user;
    }


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

    public boolean userExists(String username){
        // select * from users where username = username
        // if it returns 0, user doesn't exist - return false
        // else return true
        return true;
    }

    public boolean verifyCredentials(String username, String plainTextPassword) throws SQLException{
        Connection connection = DatabaseConnector.getConnection();
        String query = "SELECT password FROM user WHERE username = '" + username + "'";

        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet results = pstmt.executeQuery(query);

        String passwordFromDatabase = "";
        while (results.next()){
            passwordFromDatabase = results.getString("password");
        }

        // 1) use username to get encrypted password from db
        // Get salt from db
        // 3) hash+salt the plaintext password
        // compare the result from #3 to #1
        return plainTextPassword.equals(passwordFromDatabase);
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
