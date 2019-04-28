package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.io.IOException;

public class sceneChange {
    public static void sceneChangeMenuButton(String scene, MenuButton button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        try {
            Object page = FXMLLoader.load(sceneChange.class.getClassLoader().getResource(scene));

            Scene newScene = new Scene((Parent) page, 900, 500);
            Stage newStage = new Stage();

            newStage.setScene(newScene);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sceneChangeButton(String scene, Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        try {
            Object page = FXMLLoader.load(sceneChange.class.getClassLoader().getResource(scene));

            Scene newScene = new Scene((Parent) page, 900, 500);
            Stage newStage = new Stage();

            newStage.setScene(newScene);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
