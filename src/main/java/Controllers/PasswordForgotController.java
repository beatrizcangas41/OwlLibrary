package Controllers;

import database.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import util.sceneChange;

import java.sql.*;

import static util.JavaMail.getToken;
import static util.JavaMail.sendMail;
import static util.emailValidator.emailValidator;


public class PasswordForgotController {
    @FXML
    public Button goBackButton;
    @FXML
    public Button confirmEmail;

    @FXML
    private TextField uName, email1, email2;


    @FXML
    void confirmEmail(ActionEvent actionEvent) {

        Connection conn = DatabaseConnector.getConnection();
        Statement stmt = null;


        try {
            if (conn.isClosed() || conn == null) {
                System.out.println("Connection Failed");
            } else if (conn != null || !conn.isClosed()) {
                System.out.println("Connection was Successful");
                stmt = conn.createStatement();
            }

            String username1 = uName.getText();
            String emailEntered1 = email1.getText();
            String emailEntered2 = email2.getText();

            if (username1.isEmpty() || emailEntered1.isEmpty() || emailEntered2.isEmpty()) {
                System.out.println("missing credentials");

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input not valid");
                errorAlert.setContentText("Something went wrong. Please verify your input(s), something is missing. ");
                errorAlert.showAndWait();
            } else if (!username1.isEmpty() || !emailEntered1.isEmpty() || !emailEntered2.isEmpty()) {

                System.out.println("Printing : " + username1 + " " + emailEntered1);

                //language=MySQL
                String query1 = "SELECT * FROM user WHERE username is not null AND email is not null";
                ResultSet result1 = stmt.executeQuery(query1);

                if (!emailEntered1.equals(emailEntered2)) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Email address is not a match. Please try again. ");
                    errorAlert.showAndWait();
                } else {
                    String query2 = "SELECT username AND email FROM user " +
                            "WHERE username = '" + username1 + "' AND email = '" + emailEntered1 + "'";
                    ResultSet results3 = stmt.executeQuery(query2);

                    if (!results3.first()) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Input not valid");
                        errorAlert.setContentText("Wrong Credentials. Please try again. ");
                        errorAlert.showAndWait();
                    } else try {

                        String uuid = getToken();
                        // uuid.toString();

                        System.out.println("token: " + uuid);

                        // create the java mysql update preparedStatement
                        String query = "UPDATE user SET token = ? where username = ?";
                        PreparedStatement pstmt = conn.prepareStatement(query);

                        pstmt.setString(1, uuid);
                        pstmt.setString(2, username1);
                        // pstmt.setString(1, uuid.toString());

                        if (!pstmt.execute()) {
                            System.out.println("The token was added in the database. ");


                            if (emailValidator(emailEntered1)) {
                                System.out.print("The Email address " + emailEntered1 + " is valid");
                                sendMail(emailEntered1);
                                System.out.println("The email was sent. ");

                                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                                infoAlert.setHeaderText("An email has been sent out");
                                infoAlert.setContentText("Please use the token to reset your password");
                                infoAlert.showAndWait();

                                System.out.println("Confirmation Sent");

                                sceneChange.sceneChangeButton("fxml/PasswordResetUI.fxml", confirmEmail, 800, 500);

                            } else {
                                System.out.print("The Email address " + emailEntered1 + " isn't valid");

                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setHeaderText("Input not valid");
                                errorAlert.setContentText("The Email Address is not a valid address. \n\n" +
                                        "Please try registering again or Contact Us, our email is OwlLibraryBookstore@gmail.com" +
                                        " and don't forget to include your username in the email. ");
                                errorAlert.showAndWait();
                            }
                        } else {
                            System.out.println("The token could not be added. Something failed");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBackButton(ActionEvent actionEvent) {
        System.out.println("Go Back Button Pressed");

        sceneChange.sceneChangeButton("fxml/loginScreenUI.fxml", goBackButton, 800, 500);
    }
}