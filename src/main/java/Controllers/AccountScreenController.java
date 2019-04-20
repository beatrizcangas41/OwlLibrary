
package Controllers;

import database.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("Duplicates")
public class AccountScreenController {

    @FXML private Button createAccountPressed, cancelButtonPressed;

    @FXML private TextField nameTextField1;
    @FXML private TextField emailTextField1, emailTextField2;
    @FXML private TextField usernameTextField1;
    @FXML private TextField passwordField1, passwordField2;

    public void createAccountPressed() {

        Connection connection = DatabaseConnector.getConnection();
        Statement stmt2 = null;

        try {
            if (connection != null) {
                System.out.println("Connection was Successful");
                stmt2 = connection.createStatement();
            }

            String name1 = nameTextField1.getText();

            String email1 = emailTextField1.getText();
            String email2 = emailTextField2.getText();

            String uName1 = usernameTextField1.getText();

            String pwrd1 = passwordField1.getText();
            String pwrd2 = passwordField2.getText();

            if (name1.isEmpty() || email1.isEmpty() || email2.isEmpty() || uName1.isEmpty() || pwrd1.isEmpty() || pwrd2.isEmpty()) {
                System.out.println("missing credentials");

                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input not valid");
                errorAlert.setContentText("Something went wrong. Please verify your input(s), they may be empty. ");
                errorAlert.showAndWait();
            }

            else if (!name1.isEmpty() || !email1.isEmpty() || !email2.isEmpty() || !uName1.isEmpty() || !pwrd1.isEmpty() || !pwrd2.isEmpty()) {

                System.out.println("Printing : " + uName1 + "" + email1 +" " + name1 + " " + pwrd1);
                String query3 = "SELECT *" + "FROM user WHERE username is not null AND email is not null AND " +
                        "password is not null AND name is not null";

                ResultSet results3 = stmt2.executeQuery(query3);

                if (!email1.equals(email2)) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Email address is not a match. Please try again. ");
                    errorAlert.showAndWait();
                }

                else if (!pwrd1.equals(pwrd2)) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Input not valid");
                    errorAlert.setContentText("Password is not a match. Please try again. ");
                    errorAlert.showAndWait();
                }

                else {
                    String query5 = "SELECT username FROM user WHERE username = '" + uName1 + "'";
                    ResultSet results5 = stmt2.executeQuery(query5);

                    if (results5.first()) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Input not valid");
                        errorAlert.setContentText("Username already taken. Please try again. ");
                        errorAlert.showAndWait();
                    }

                    else {
                        String query4 = "SELECT email FROM user WHERE email = '" + email1 + "'";
                        ResultSet results4 = stmt2.executeQuery(query4);

                        if (results4.first()) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setHeaderText("Input not valid");
                            errorAlert.setContentText("Email already taken. Please try again. ");
                            errorAlert.showAndWait();
                        }

                        else {
                            Statement s = connection.createStatement();
                            s.executeUpdate("INSERT INTO `user`(email, name, username, password)" +
                                    " VALUE ('" + email1 + "' , '" + name1 + "', '" + uName1 + "', '" + pwrd1 + "')");

                            System.out.println("The account was inserted in the database. ");

                            try {
                                Stage stage = (Stage) createAccountPressed.getScene().getWindow();
                                stage.close();

                                Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScreenUI.fxml"));

                                Scene newScene = new Scene((Parent) page, 900, 500);
                                Stage newStage = new Stage();

                                newStage.setScene(newScene);

                                newStage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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