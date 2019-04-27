package database;

// In this class you will be able to find all queries related to the Book and maybe Library classes

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDatabaseHandler {
    private static Connection connection = DatabaseConnector.getConnection();

    public static ResultSet getBooks() throws SQLException {

        String query1 = "SELECT * FROM book";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        return pstmt.executeQuery(query1);
    }
}
