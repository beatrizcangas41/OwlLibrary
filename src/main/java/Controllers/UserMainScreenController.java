package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class UserMainScreenController {

    public void logoutButtonPressed(ActionEvent actionEvent) {
        // go to normal page
        Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainStage.close();

        try {
            GridPane gridPane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScreenUI.fxml"));
            Scene newScene = new Scene(gridPane);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
