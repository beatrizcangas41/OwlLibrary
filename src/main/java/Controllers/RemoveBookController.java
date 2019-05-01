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

import static database.Book_OrderDatabaseHandler.*;
import static util.sceneChange.sceneChangeButton;

public class RemoveBookController {
    @FXML public TextField titleTextField, authorTextField;
    @FXML public Button removeButtonPressed, cancelButtonPressed, titleButton, authorButton;

    public void removeButtonPressed(ActionEvent actionEvent) {
        Connection connection = DatabaseConnector.getConnection();
        Statement stmt2 = null;

        String title = titleTextField.getText();
        String author = titleTextField.getText();

        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("empty field(s)");

            String message = "Something went wrong. All inputs are required. Please try again. ";
            dialogCreator.displayErrorDialog("Input not valid", message);
        }

        else
            System.out.println("Entered info : " + title + " " + author);

        if (connection != null) {
            System.out.println("Connection Successful");

            try {
                if (verifyBookByAuthorAndTitle(title, author)) {

                    deleteBook(title, author);
                    sceneChange.sceneChangeButton("fxml/AdminMainScreenUI.fxml", removeButtonPressed, 1200, 800);

                } else {

                    String message = "The book does not exists ";
                    dialogCreator.displayErrorDialog("Input not valid", message);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else System.out.println("Connection Fails");
    }

    public void cancelButtonPressed(ActionEvent actionEvent) {
        sceneChangeButton("fxml/AdminMainScreenController", cancelButtonPressed, 1200, 800);
    }
}
