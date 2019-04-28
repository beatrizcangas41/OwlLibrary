package Controllers;

import database.UserDatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.sceneChange;

import java.sql.SQLException;

import static database.UserDatabaseHandler.*;
import static util.dialogCreator.displayErrorDialog;

public class loginScreenController {

    private UserDatabaseHandler userDatabaseHandler;

    @FXML
    public void initialize() {
        userDatabaseHandler = new UserDatabaseHandler();
    }

    @FXML
    private Button registerButtonPressed, loginButtonPressed, forgotPasswordButtonPressed;

    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;

    @FXML
    public void loginButtonPressed() {

        String uName = usernameTextField.getText();
        String pwrd = passwordField.getText();

        try {

            if (userExists(uName)) {
                if (!verifyLoginCredentials(uName, pwrd)) {
                    displayErrorDialog("Input not valid", "Wrong Username or Password");
                } else {
                    String user_type = getUserTypeFromUsername(uName);

                    if (user_type.equals("Admin")) {
                        System.out.println("Admin Page");

                        sceneChange.sceneChangeButton("fxml/AdminMainScreenUI.fxml", loginButtonPressed, 1200, 800);

                    } else if (user_type.equals("User")) {
                        Stage loginStage = (Stage) passwordField.getScene().getWindow();

                        System.out.println("User Page");

                        sceneChange.sceneChangeButton("fxml/UserMainScreenUI.fxml", loginButtonPressed, 1200, 800);
                    } else displayErrorDialog("Error", "The user has not been classified ");
                }
            }
            else {
                displayErrorDialog("user does not exist", "please try again");
            }
        } catch (SQLException e) {
            displayErrorDialog("SQL Error", "Unable to verify credentials");
        }
    }

    public void registerButtonPressed(ActionEvent actionEvent) {

        System.out.println("The account page has been loaded");
        sceneChange.sceneChangeButton("fxml/AccountScreenUI.fxml", registerButtonPressed, 800, 500);
    }
    
    public void forgotPasswordButtonPressed(ActionEvent actionEvent) {
        System.out.println("The account page has been loaded");
        sceneChange.sceneChangeButton("fxml/ForgotPasswordUI.fxml", registerButtonPressed, 800, 500);
    }
}