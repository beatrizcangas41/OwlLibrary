package database;

import model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;

import static util.JavaMail.getToken;

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
            user = new User(name, username, email, password);
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

        String uName = null;

        System.out.println("username: " + username);
        System.out.println("dbUname: " + uName);


        while (results.next()) {
            uName = results.getString("username");

            System.out.println("username: " + username);
            System.out.println("dbUname: " + uName);
        }
        if (uName != null && username != null) {
            if (uName.equals(username)) {
                System.out.println("User exists");
                return true;
            } else {
                System.out.println("User does NOT exists");

                System.out.println("username: " + username);
                System.out.println("dbUname: " + uName);
                return false;
            }
        }

        else System.out.println("User does NOT exists");

        return username.equals(uName);
    }

    public static boolean verifyLoginCredentials (String username, String password) throws SQLException {

        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        System.out.println("pw entered: " + password);

        String dbPassword = null;

        while (results.next()) {
            dbPassword = results.getString("password");
            System.out.println("db pw: " + dbPassword);
            System.out.println("pw entered: " + password);
        }

        if (BCrypt.checkpw(password, dbPassword)) {
            System.out.println("It matches");
            return true;
        } else {
            System.out.println("It does not match");
            System.out.println("db pw: " + dbPassword);
            System.out.println("pw entered: " + password);
            return false;
        }

    }

    public static boolean verifyEmail(String username, String email) throws SQLException {

        String query = "SELECT email FROM user WHERE username = '" + username + "'";

        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet results = pstmt.executeQuery(query);

        String emailFromDatabase = "";
        while (results.next()){
            emailFromDatabase = results.getString("email");
        }

        return email.equals(emailFromDatabase);
    }

    public static String getUserTypeFromUsername(String username) throws SQLException {
        String query1 = "SELECT user_type FROM user WHERE username = '" + username + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results1 = pstmt.executeQuery(query1);

        String usertype = null;

        while (results1.next()) {
            usertype = results1.getString("user_type");
        }

        System.out.println("user_type: " + usertype);

        if (usertype.equals("Admin")) System.out.println("Admin Page");
        else if (usertype.equals("User")) System.out.println("User Page");
        else System.out.println("User not classified");

        return usertype;
    }

    public static boolean verifyToken (String token) throws SQLException {
        String query1 = "SELECT * FROM user WHERE token = '" + token + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results1 = pstmt.executeQuery(query1);

        String token1 = null;

        while (results1.first()) {
            token1 = results1.getString("token");
        }

        System.out.println("token: " + token);
        System.out.println("dbToken: " + token1);

        if (token1.equals(getToken())) {
            System.out.println("token is a match. ");
            return true;
        }
        else {
            System.out.println("wrong input");
            return false;
        }
    }
}
