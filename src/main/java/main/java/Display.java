package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Display extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScreenUI.fxml"));
            Scene scene = new Scene((Parent) page, 800, 400);
            primaryStage.setTitle("Owl Library");

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}