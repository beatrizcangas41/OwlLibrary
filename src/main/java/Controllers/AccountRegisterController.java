
package Controllers;

import database.DatabaseConnector;
import database.UserDatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.dialogCreator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountRegisterController {

    private UserDatabaseHandler userDatabaseHandler;

    @FXML
    public void initialize() {
        userDatabaseHandler = new UserDatabaseHandler();
    }

    @FXML
    private Button createAccountPressed, cancelButtonPressed;

    @FXML
    private TextField nameTextField1, emailTextField1, emailTextField2,
                      usernameTextField1, passwordField1, passwordField2;

    public void createAccountPressed() throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Statement stmt2 = null;

        String name1 = nameTextField1.getText();
        String email1 = emailTextField1.getText();
        String email2 = emailTextField2.getText();
        String uName1 = usernameTextField1.getText();
        String pwrd1 = passwordField1.getText();
        String pwrd2 = passwordField2.getText();

        if (name1.isEmpty() || email1.isEmpty() || email2.isEmpty() ||
                uName1.isEmpty() || pwrd1.isEmpty() || pwrd2.isEmpty()) {
            System.out.println("missing credentials");

            String message = "Something went wrong. Please verify your input(s), they may be empty. ";
            dialogCreator.displayErrorDialog("Input not valid", message);
        }

        else if (!name1.isEmpty() && !email1.isEmpty() && !email2.isEmpty() &&
                !uName1.isEmpty() && !pwrd1.isEmpty() && !pwrd2.isEmpty()) {

            if (!email1.equals(email2)) {
                String message = "Email address is not a match. Please try again. ";
                dialogCreator.displayErrorDialog("Input not valid", message);
            }

            else if (!pwrd1.equals(pwrd2)) {
                String message = "Password is not a match. Please try again. ";
                dialogCreator.displayErrorDialog("Input not valid", message);
            }

            else {
                System.out.println("Entered credentials : " + uName1 + " " + email1 + " " + name1 + " " + pwrd1);

                if (connection != null) {
                    System.out.println("Connection Successful");

                    boolean userExists = UserDatabaseHandler.userExists(uName1);
                    boolean verifyEmail = UserDatabaseHandler.verifyEmail(uName1, email1);

                    try {
                        if (userExists || verifyEmail) {
                            if (userExists && verifyEmail) {
                                String message = "Username and email already taken. Please try again. ";
                                dialogCreator.displayErrorDialog("Input not valid", message);
                            }

                            else if (userExists) {
                                String message = "Username already taken. Please try again. ";
                                dialogCreator.displayErrorDialog("Input not valid", message);
                            }
                            else if(verifyEmail) {
                                String message = "Email already taken. Please try again. ";
                                dialogCreator.displayErrorDialog("Input not valid", message);
                            }
                        }

                        else if (!userExists && !verifyEmail) {

                            UserDatabaseHandler.addUser(name1, email1, uName1, pwrd1);

                            System.out.println("The account was inserted in the database. ");

                            Stage stage = (Stage) createAccountPressed.getScene().getWindow();
                            stage.close();

                            Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScreenUI.fxml"));

                            Scene newScene = new Scene((Parent) page, 900, 500);
                            Stage newStage = new Stage();

                            newStage.setScene(newScene);

                            newStage.show();
                        }

                        else {
                            String message = "credentials are already taken. Please try again";
                            dialogCreator.displayErrorDialog("Input not valid", message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                else {
                    System.out.println("Connection Fails");
                }

            }
        }
    }

    public void cancelButtonPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel Button Pressed");

        Stage stage = (Stage) cancelButtonPressed.getScene().getWindow();
        stage.close();

        Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScreenUI.fxml"));

        Scene newScene = new Scene((Parent) page, 900, 500);
        Stage newStage = new Stage();

        newStage.setScene(newScene);
        newStage.show();
    }
}