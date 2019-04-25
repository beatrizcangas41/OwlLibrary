package Controllers;

import database.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static util.dialogCreator.displayErrorDialog;
import static util.dialogCreator.displayInformationDialog;

public class PasswordResetController {
    @FXML  public Button confirmPassword, cancelButton;
    @FXML  public TextField password1, password2, token;

    @FXML
    public void confirmPassword(ActionEvent actionEvent) throws SQLException {
        Connection conn = DatabaseConnector.getConnection();

        String pw1 = password1.getText();
        String pw2 = password2.getText();
        String token1 = token.getText();

        if (!pw1.equals(null) && !pw2.equals(null) && !token1.equals(null)) {

            System.out.println("credentials are not null");

            if (pw1.equals(pw2)) {

                System.out.println("pw1 = pw2");

                String query2 = "SELECT * FROM user WHERE token = '" + token1 + "'";
                PreparedStatement pstmt = conn.prepareStatement(query2);
                ResultSet results2 = pstmt.executeQuery(query2);

                String token2 = null;

                while (results2.next()) {
                    token2 = results2.getString("token");
                }

                System.out.println("entered: " + token1);
                System.out.println("DBToken: " + token2);

                if (token2.equals(token1)) {

                    String hashpw = BCrypt.hashpw(pw1, BCrypt.gensalt());
                    System.out.println("hashed pw: " + hashpw);

                    System.lineSeparator().equals("tokens are a match. ");

                    String query = "UPDATE user SET password = ? where token = ?";
                    PreparedStatement pstmt1 = conn.prepareStatement(query);

                    pstmt1.setString(1, hashpw);
                    pstmt1.setString(2, token1);

                    if (!pstmt1.execute()) {
                        try {
                            System.out.println("password updated");

                            displayInformationDialog("Confirmation", "your password is been updated. ");


                            Stage stage = (Stage) confirmPassword.getScene().getWindow();
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

            else {
                displayErrorDialog("Input not valid", "Verify your credentials. Please try again. ");

                try {
                    Stage stage = (Stage) confirmPassword.getScene().getWindow();
                    stage.close();

                    Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ForgotPasswordUI.fxml"));

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

    public void cancelButton(ActionEvent actionEvent) throws IOException {
        System.out.println("Go Back Button Pressed");

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScreenUI.fxml"));

        Scene newScene = new Scene((Parent) page, 900, 500);
        Stage newStage = new Stage();

        newStage.setScene(newScene);
        newStage.show();
    }
}
