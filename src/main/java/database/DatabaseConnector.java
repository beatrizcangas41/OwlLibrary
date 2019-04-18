package database;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnector {
    private static final String url =  "jdbc:mysql://localhost:3306/owllibrary";
    private static final String user =  "root";
    private static final String password =  "";
    private Connection con = null;


    public static Connection getConnection(){
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            // create a connection to the database
            return DriverManager.getConnection(url, user, password);
        }

        catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
