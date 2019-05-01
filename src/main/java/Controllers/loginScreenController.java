package Controllers;

import database.UserDatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.sceneChange;

import java.io.IOException;
import java.sql.SQLException;

import static database.UserDatabaseHandler.*;
import static util.dialogCreator.displayErrorDialog;

public class loginScreenController {

    private UserDatabaseHandler userDatabaseHandler;
    private UserMainScreenController userMainScreenController;

    @FXML private Button registerButtonPressed, loginButtonPressed, forgotPasswordButtonPressed;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Label lblFromController2;

    private String uName;
    private String pwrd;

    @FXML public void initialize() {
        userDatabaseHandler = new UserDatabaseHandler();
        userMainScreenController = new UserMainScreenController();
    }

    @FXML
    public void loginButtonPressed() throws IOException {

        uName = usernameTextField.getText();
        pwrd = passwordField.getText();

        try {

            if (userExists(uName)) {
                if (!verifyLoginCredentials(uName, pwrd))
                    displayErrorDialog("Input not valid", "Wrong Username or Password");
                else {
                    String user_type = getUserTypeFromUsername(uName);

                    if (user_type.equals("Admin")) {
                        System.out.println("Admin Page");

                        sceneChange.sceneChangeButton("fxml/AdminMainScreenUI.fxml", loginButtonPressed, 1200, 800);

                    } else if (user_type.equals("User")) {

                        Stage loginStage = (Stage) passwordField.getScene().getWindow();
                        loginStage.close();

                        System.out.println("User Page");

                        //Load second scene
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/UserMainScreenUI.fxml"));
                        Parent root = loader.load();

                        userMainScreenController = loader.getController();
                        userMainScreenController.setName(uName);

                        //Show scene 2 in new window
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 1200, 800));
                        stage.setTitle("Owl Library");
                        stage.show();
                    }

                    else displayErrorDialog("Error", "The user has not been classified ");
                }
            }
            else displayErrorDialog("user does not exist", "please try again");

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