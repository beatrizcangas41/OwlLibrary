package util;

import javafx.scene.control.Alert;

public class DialogCreator {

    public static void displayErrorDialog(String header, String message){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void displayInformationDialog(String header, String message){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
