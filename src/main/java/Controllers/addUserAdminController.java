package Controllers;

import database.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import util.sceneChange;

import java.sql.Connection;
import java.sql.Statement;

import static database.UserDatabaseHandler.*;
import static util.dialogCreator.displayErrorDialog;
import static util.emailValidator.emailValidator;

public class addUserAdminController {
    public TextField nameTextField1, emailTextField1, emailTextField2, usernameTextField1, passwordField1, passwordField2, usertype;
    public Button createAccountPressed, cancelButtonPressed;

    public void createAccountPressed() {
        Connection connection = DatabaseConnector.getConnection();
        Statement stmt2 = null;

        String name1 = nameTextField1.getText();
        String email1 = emailTextField1.getText();
        String email2 = emailTextField2.getText();
        String uName1 = usernameTextField1.getText();
        String pwrd1 = passwordField1.getText();
        String pwrd2 = passwordField2.getText();
        String utype1 = usertype.getText();

        if (name1.isEmpty() || email1.isEmpty() || email2.isEmpty() ||
                uName1.isEmpty() || pwrd1.isEmpty() || pwrd2.isEmpty()) {
            System.out.println("missing credentials");

            String message = "Something went wrong. Please verify your input(s), they may be empty. ";
            displayErrorDialog("Input not valid", message);
        }

        else if (!name1.isEmpty() && !email1.isEmpty() && !email2.isEmpty() &&
                !uName1.isEmpty() && !pwrd1.isEmpty() && !pwrd2.isEmpty()) {

            if (!email1.equals(email2)) {
                String message = "Email address is not a match. Please try again. ";
                displayErrorDialog("Input not valid", message);
            }

            else if (!pwrd1.equals(pwrd2)) {
                String message = "Password is not a match. Please try again. ";
                displayErrorDialog("Input not valid", message);
            }

            else {
                System.out.println("Entered credentials : " + uName1 + " " + email1 + " " + name1 + " " + pwrd1);

                if (connection != null) {
                    System.out.println("Connection Successful");

                    try {
                        if (userExists(uName1) || verifyEmail(uName1, email1)) {
                            if (userExists(uName1) && verifyEmail(uName1, email1)) {
                                String message = "Username and email already taken. Please try again. ";
                                displayErrorDialog("Input not valid", message);
                            }

                            else if (userExists(uName1)) {
                                String message = "Username already taken. Please try again. ";
                                displayErrorDialog("Input not valid", message);
                            }

                            else if(verifyEmail(uName1, email1)) {
                                String message = "Email already taken. Please try again. ";
                                displayErrorDialog("Input not valid", message);
                            }
                        }

                        else {
                            if (!emailValidator(email1)) {
                                String message = "Invalid email, wrong format. Please try again. ";
                                displayErrorDialog("Input not valid", message);
                            }

                            else {

                                if (utype1.equals("User")) {
                                    addUser(name1, email1, uName1, pwrd1);
                                    System.out.println("User successfully created");
                                }

                                else if (utype1.equals("Admin")) {
                                    addUserAdmin(name1, email1, uName1, pwrd1, utype1);
                                    System.out.println("Admin user successfully created");
                                }

                                else {
                                    System.out.println("USer was not properly set");
                                    displayErrorDialog("Input not valid", "The User Type was not properly set");
                                }

                                sceneChange.sceneChangeButton("fxml/UserMainScreenUI.fxml", createAccountPressed, 1200, 800);

                            }
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

    public void cancelButtonPressed(ActionEvent actionEvent) {
        System.out.println("Cancel Button Pressed");

        sceneChange.sceneChangeButton("fxml/loginScreenUI.fxml", cancelButtonPressed, 800, 500);

    }
}
