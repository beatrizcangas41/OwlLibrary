package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDatabaseHandler {
    private static Connection connection = DatabaseConnector.getConnection();

    public static ResultSet getBooks() throws SQLException {

        String query = "SELECT * FROM book";
        PreparedStatement pstmt = connection.prepareStatement(query);
        return pstmt.executeQuery(query);
    }
}
