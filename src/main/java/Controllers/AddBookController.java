package Controllers;

import database.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import util.dialogCreator;
import util.sceneChange;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static database.Book_OrderDatabaseHandler.addBook;
import static database.Book_OrderDatabaseHandler.verifyBookByAuthorAndTitle;
import static util.sceneChange.sceneChangeButton;

public class AddBookController {


    @FXML public Button addBookButton, cancelButtonPressed;
    public TextField titleFiled, authorField, descriptionField, priceField;
    public Button titleButton, authorButton, descriptionButton, priceButton;

    public void addBookButton(ActionEvent actionEvent) {
        Connection connection = DatabaseConnector.getConnection();
        Statement stmt2 = null;

        String title = titleFiled.getText();
        String author = authorField.getText();
        String description = descriptionField.getText();

        try {
            Double price = Double.parseDouble(priceField.getText());

            if (title.isEmpty() || author.isEmpty() || description.isEmpty() || price.equals(null)) {
                System.out.println("empty field(s)");

                String message = "Something went wrong. All inputs are required. Please try again. ";
                dialogCreator.displayErrorDialog("Input not valid", message);
            }

            else
                System.out.println("Entered info : " + title + " " + author + " " + description + " " + price);

            if (connection != null) {
                System.out.println("Connection Successful");

                try {
                    if (verifyBookByAuthorAndTitle(title, author)) {
                        String message = "The book already exists ";
                        dialogCreator.displayErrorDialog("Input not valid", message);
                    } else {
                        addBook(title, author, description, price);

                        sceneChange.sceneChangeButton("fxml/AdminMainScreenUI.fxml", addBookButton, 800, 500);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else System.out.println("Connection Fails");

        } catch (NumberFormatException e) {
            dialogCreator.displayErrorDialog("Input not valid", "The field 'Price' must be a number");
        }
    }

    public void cancelButtonPressed(ActionEvent actionEvent) {
        sceneChangeButton("fxml/AdminMainScreenUI.fxml", cancelButtonPressed, 1200, 800);
    }
}
