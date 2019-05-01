package database;

import java.sql.*;
import java.util.Objects;

public class Book_OrderDatabaseHandler {
    private static Connection connection = DatabaseConnector.getConnection();

    public static ResultSet getBooks() throws SQLException {
        String query = "SELECT * FROM book";
        PreparedStatement pstmt = connection.prepareStatement(query);
        return pstmt.executeQuery(query);
    }

    public static String getBookFromTitle(String title) throws SQLException {

        String formattedString = title.replace("[", "").replace("]", "");
        //String string = formattedString.replaceAll("[\\[\\](){}]","");

        String query = "SELECT bookID FROM book where title = '" + title + "'";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet results = pstmt.executeQuery(query);

        String bookID = null;

        while (results.next()) {
            bookID = results.getString("bookID");
        }

        return bookID;
    }

    public static void addBook(String title, String author, String description, Double price) throws SQLException {

        Statement s = connection.createStatement();
        s.executeUpdate("INSERT INTO `book`(title, author, description, price)" +
                " VALUE ('" + title + "' , '" + author + "', '" + description + "', '" + price + "')");
    }

    public static void deleteBook(String title, String author) throws SQLException {
        String query = "DELETE from book where title = ? AND author = ?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, title);
        preparedStmt.setString(2, author);

        preparedStmt.execute();
    }

    public static ResultSet getInfoFromOrderTable(String username) throws SQLException {

        String query = "SELECT * FROM `order` where username = '" + username + "'";
        PreparedStatement pstmt = connection.prepareStatement(query);

        return pstmt.executeQuery(query);
    }

    public static String getAddressFromUsername (String username) throws SQLException {

        String query = "SELECT address FROM user where username = '" + username + "'";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet results = pstmt.executeQuery(query);

        String address = null;

        while (results.next()) {
            address = results.getString("address");
        }

        return address;

    }

    public static ResultSet checkAddress(String username, String dbAddress) throws SQLException {
        String query1 = "SELECT * FROM user WHERE username = '" + username + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        while (results.next()) {
            dbAddress = results.getString("address");
            System.out.println("db address: " + dbAddress);
        }

        return results;
    }

    public static boolean updateAddress(String address, String username) throws SQLException {

        String query = "UPDATE user SET address = ? where username = ?";
        PreparedStatement pstmt1 = connection.prepareStatement(query);

        pstmt1.setString(1, address);
        pstmt1.setString(2, username);

        return pstmt1.execute();
    }

    public static void addOrder(String title, String bookID, String username, String shipping_address) throws SQLException {

        Statement s = connection.createStatement();
        s.executeUpdate("INSERT INTO `order_info` (bookID, bookTitle, username, shipping_address) " +
                "VALUES ('" + bookID + "', '" + title + "','" + username + "', '" + shipping_address +"')");
    }

    public static boolean verifyBookByAuthorAndTitle(String title, String author) throws SQLException {
        String query1 = "SELECT * FROM book WHERE title = '" + title + "' AND author = '" + author + "'";
        PreparedStatement pstmt = connection.prepareStatement(query1);
        ResultSet results = pstmt.executeQuery(query1);

        String dbTitle = null;
        // String dbAuthor = null;

        while (results.next()) {
            dbTitle = results.getString("title");
            //dbAuthor = results.getString("author");

            System.out.println("db title: " + dbTitle);
            // System.out.println("db author: " + dbAuthor);
        }

        if (Objects.equals(dbTitle, title)) return true;
        else return false;
    }
}
