package database;

import model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import util.passwordEncryption;

import java.sql.*;

import static util.dialogCreator.displayErrorDialog;

public class UserDatabaseHandler {
    private static Connection connection = DatabaseConnector.getConnection();

    public static User getUserByUsername(String username) throws SQLException{
        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";

        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        User user = null;
        while (results.next()){
            String name = results.getString("name");
            String email = results.getString("email");
            String user_type = results.getString("user_type");
            String password = results.getString("password");
            user = new User(name, email, username, password);
        }

        return user;
    }

    public static void addUser(String name, String email, String username, String password) throws SQLException {

        Statement s = connection.createStatement();
        password = BCrypt.hashpw(password, BCrypt.gensalt());

        s.executeUpdate("INSERT INTO `user`(name, email, username, password)" +
                " VALUE ('" + name + "' , '" + email + "', '" + username + "', '" + password + "')");
    }

    public static boolean userExists (String username) throws SQLException {
        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";

        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        User user = null;
        while (results.next()){
            String password = results.getString("password");
        }
        if (user != null) {
            System.out.println("User exists");
            return true;
        } else {
            System.out.println("User does NOT exists");
            return false;
        }
    }

    public static boolean verifyLoginCredentials (String username, String password) throws SQLException {
        User user = getUserByUsername(username);

        boolean verifyMatch = false;
        if (user != null) {
            String query1 = "SELECT password FROM user WHERE username = '" + username + "'";
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet results = pstmt.executeQuery(query1);

            String databasePassword = results.getString(password);
            // passwordMatch = BCrypt.checkpw(password, databasePassword);

            verifyMatch = passwordEncryption.verifyHash(databasePassword, password);

            System.out.println("db pw: " + databasePassword);
            System.out.println("pw entered: " + password);

            if (verifyMatch) {
                System.out.println("It matches");
                System.out.println("db pw: " + databasePassword);
                System.out.println("pw entered: " + password);
                return true;
            } else {
                System.out.println("It does not match");
                System.out.println("db pw: " + databasePassword);
                System.out.println("pw entered: " + password);
                displayErrorDialog("Invalid input", "please try again");
                return false;
            }
        }
        return verifyMatch;
    }

        public static boolean verifyEmail(String username, String email) throws SQLException {

            Connection connection = DatabaseConnector.getConnection();
            String query = "SELECT email FROM user WHERE username = '" + username + "'";

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet results = pstmt.executeQuery(query);

            String emailFromDatabase = "";
            while (results.next()){
                emailFromDatabase = results.getString("email");
            }

            return email.equals(emailFromDatabase);
        }

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
    }
