package util;

import javafx.scene.control.Alert;

public class dialogCreator {

    public static void displayErrorDialog(String header, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void displayInformationDialog(String header, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void displayConfirmationDialog(String header, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
