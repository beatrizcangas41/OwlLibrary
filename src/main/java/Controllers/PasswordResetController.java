package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordResetController {
    @FXML
    public Button confirmPassword, cancelButton;
    @FXML
    public TextField password1, password2;

    public void confirmPassword(ActionEvent actionEvent) {


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
