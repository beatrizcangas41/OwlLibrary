package Controllers;

import database.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class loginScreenController {
    @FXML public Button registerButtonPressed, loginButtonPressed;

    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;

    public void loginButtonPressed() {

        Connection connection = DatabaseConnector.getConnection();
        Statement stmt = null;

        try {
            if (connection != null) {
                System.out.println("Connection was Successful");
                stmt = connection.createStatement();
            }

            String uName = usernameTextField.getText();
            String pwrd = passwordField.getText();

            if (uName == null || pwrd == null) System.out.println("missing credentials");

            else if (uName != null && pwrd != null) {
                String query = "SELECT username AND password " +
                        "FROM user WHERE username = '" + uName + "' AND password = '" + pwrd + "'";

                System.out.println("Database: " + "username: " + uName + " password: " + pwrd);

                assert stmt != null;
                ResultSet results = stmt.executeQuery(query);

                if (!results.first()) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Wrong Username or Password");
                    errorAlert.showAndWait();
                }

                else {
                    System.out.println("Inputted Data: " + "username: " + uName + " password: " + pwrd);

                    String query1 = "SELECT user_type FROM user WHERE username = '" + uName + "' AND password = '" + pwrd + "'";

                    stmt = connection.createStatement();

                    ResultSet results1 = stmt.executeQuery(query1);

                    String usertype = null;
                    if (results1.first()) {
                        usertype = results1.getString(1);
                    }

                    System.out.println("user_type: " + usertype);

                    assert usertype != null;
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
        } catch (SQLException e1) {
            e1.printStackTrace();
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


}