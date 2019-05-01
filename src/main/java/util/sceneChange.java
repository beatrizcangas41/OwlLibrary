package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.io.IOException;

public class sceneChange {

    public static void sceneChangeMenuButton(String fxml, MenuButton button, int width, int height) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        try {
            Object page = FXMLLoader.load(sceneChange.class.getClassLoader().getResource(fxml));

            Scene newScene = new Scene((Parent) page, width, height);
            Stage newStage = new Stage();
            newStage.setTitle("Owl Library");

            newStage.setScene(newScene);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FXMLLoader sceneChangeButton(String fxml, Button button, int width, int height) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        try {
            Object page = FXMLLoader.load(sceneChange.class.getClassLoader().getResource(fxml));

            Scene newScene = new Scene((Parent) page, width, height);
            Stage newStage = new Stage();
            newStage.setTitle("Owl Library");

            newStage.setScene(newScene);
            newStage.show();
            return (FXMLLoader) newScene.getUserData();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FXMLLoader sceneChangeButtonPopup(String fxml, int width, int height) {

        try {
            Object page = FXMLLoader.load(sceneChange.class.getClassLoader().getResource(fxml));

            Scene newScene = new Scene((Parent) page, width, height);
            Stage newStage = new Stage();
            newStage.setTitle("Owl Library");

            newStage.setScene(newScene);
            newStage.show();
            return (FXMLLoader) newScene.getUserData();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
