package database;

import model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;

import static util.JavaMailUtil.getToken;

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
                " VALUE ('" + name + "' , '" + email + "', '" + username + "', '" + password.hashCode() + "')");
    }

    public static boolean userExists (String username) throws SQLException {
        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";

        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        String uName = null;

        try {
            while (results.next()) {
                uName = results.getString("username");
                System.out.println("username: " + username);
            }
            if (uName.equals(username)) {
                System.out.println("User exists");
                return true;
            } else {
                System.out.println("User does NOT exists");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uName.equals(username);
    }

    public static boolean verifyLoginCredentials (String username, String password) throws SQLException {
        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        password = BCrypt.hashpw(password, BCrypt.gensalt());

        String dbPassword = null;

        try {
            while (results.next()) {
                dbPassword = results.getString("password");
                System.out.println("db pw: " + dbPassword);
                System.out.println("pw entered: " + password);
            }

            if (password.equals(dbPassword)) {
                System.out.println("It matches");
                return true;
            }

            else {
                System.out.println("It does not match");
                System.out.println("db pw: " + dbPassword);
                System.out.println("pw entered: " + password);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password.equals(dbPassword);
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


    public static String getUserTypeFromCredentials(String username, String password) throws SQLException {
        String query1 = "SELECT user_type FROM user WHERE username = '" + username + "' AND password = '" + password + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results1 = pstmt.executeQuery(query1);

        String usertype = String.valueOf(results1);

        if (results1.first()) {
            usertype = results1.getString("user_type");
        }

        System.out.println("user_type: " + usertype);

        if (usertype.equals("Admin")) System.out.println("Admin Page");
        else if (usertype.equals("User")) System.out.println("User Page");
        else System.out.println("User not classified");

        return usertype;
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
