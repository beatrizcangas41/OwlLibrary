package Controllers;

import database.DatabaseConnector;
import database.UserDatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import util.DialogCreator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginScreenController {

    private UserDatabaseHandler userDatabaseHandler;


    @FXML
    public void initialize() {
        userDatabaseHandler = new UserDatabaseHandler();
    }

    @FXML
    private Button registerButtonPressed, loginButtonPressed, forgotPasswordButtonPressed;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginButtonPressed() {


        String uName = usernameTextField.getText();
        String pwrd = passwordField.getText();


        boolean validCredentials = false;
        try {
            validCredentials = userDatabaseHandler.verifyCredentials(uName, pwrd);
        } catch (SQLException e) {
            DialogCreator.displayErrorDialog("SQL Error", "Unable to verify credentials");
        }

        if (!validCredentials){
            DialogCreator.displayErrorDialog("Input not valid", "Wrong Username or Password");
            return;
        }



        // Put the rest of the logic here
        try {
            User user = userDatabaseHandler.getUserByUsername(uName);
            System.out.println(user);
        } catch (SQLException e) {
            DialogCreator.displayErrorDialog("SQL Error", "Unable to get user");
        }




    }

    @FXML
    public void loginButtonPressed2() throws SQLException {

        DatabaseConnector dbConn = new DatabaseConnector();
        Connection conn = DatabaseConnector.getConnection();

        PreparedStatement pstmt;

        if (conn.isClosed()) {
            System.out.println("Connection Failed");
        } else {
            try {
                String SQL = "SELECT `username`,`password` FROM `user` WHERE `username` =? AND `password` = ?;";
                pstmt = conn.prepareStatement(SQL);

                String uName = usernameTextField.getText();
                String pwrd = passwordField.getText();

                if (uName.isEmpty() || pwrd.isEmpty()) {
                    System.out.println("missing credentials");

                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("All fields are required. ");
                    errorAlert.showAndWait();
                } else if (!uName.isEmpty() && !pwrd.isEmpty()) {
                    String query = "SELECT username AND password " +
                            "FROM user WHERE username = '" + uName + "' AND password = '" + pwrd + "'";

                    System.out.println("Database: " + "username: " + uName + " password: " + pwrd);

                    ResultSet results = pstmt.executeQuery(query);

                    if (!results.first()) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Input not valid");
                        errorAlert.setContentText("Wrong Username or Password");
                        errorAlert.showAndWait();
                    } else {
                        System.out.println("Inputted Data: " + "username: " + uName + " password: " + pwrd);

                        String query1 = "SELECT user_type FROM user WHERE username = '" + uName + "' AND password = '" + pwrd + "'";

                        pstmt = conn.prepareStatement(query1);

                        ResultSet results1 = pstmt.executeQuery(query1);

                        String usertype = null;
                        if (results1.first()) {
                            usertype = results1.getString(1);
                        }

                        System.out.println("user_type: " + usertype);

                        if (usertype.equals("Admin")) {

                            System.out.println("Admin Page");

                            try {
                                Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AdminMainScreenUI.fxml"));
                                Scene newScene = new Scene((Parent) page, 500, 500);

                                Stage newStage = new Stage();
                                newStage.setScene(newScene);
                                newStage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Stage loginStage = (Stage) passwordField.getScene().getWindow();

                            System.out.println("User Page");

                            loginStage.close();

                            try {

                                Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/UserMainScreenUI.fxml"));
                                Scene newScene = new Scene((Parent) page, 500, 500);

                                Stage newStage = new Stage();
                                newStage.setScene(newScene);
                                newStage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerButtonPressed(ActionEvent actionEvent) throws IOException {

        System.out.println("The account page has been loaded");

        Stage stage = (Stage) registerButtonPressed.getScene().getWindow();
        stage.close();

        Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AccountScreenUI.fxml"));

        Scene newScene = new Scene((Parent) page, 900, 500);
        Stage newStage = new Stage();

        newStage.setScene(newScene);
        newStage.show();
    }


    public void forgotPasswordButtonPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("The account page has been loaded");

        Stage stage = (Stage) registerButtonPressed.getScene().getWindow();
        stage.close();

        Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ForgotPasswordUI.fxml"));

        Scene newScene = new Scene((Parent) page, 900, 500);
        Stage newStage = new Stage();

        newStage.setScene(newScene);
        newStage.show();
    }
}