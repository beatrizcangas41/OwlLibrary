package Controllers;

import database.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.dialogCreator;
import util.sceneChange;

import java.sql.Connection;
import java.sql.SQLException;

import static database.Book_OrderDatabaseHandler.updateAddress;
import static util.dialogCreator.displayInformationDialog;

public class AddressUpdateController {
    @FXML public Button submitAddress, cancelButton;
    @FXML public TextField current, new1, new2, username;
    public Button usernameButton;

    void setUsername(String name) {
        username.setText(name);
    }

    String getUsername() {
        return username.getText();
    }

    void setAddress(String address) {
        current.setText(address);
    }

    @FXML
    public void submitAddress(ActionEvent actionEvent) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();

        String address1 = new1.getText();
        String address2 = new2.getText();


        if (address1.equals(address2)) {
            boolean address = updateAddress(address1, username.getText());

            if (!address) {

                System.out.println("address updated");
                displayInformationDialog("Confirmation", "your address is been updated. ");

                Stage stage = (Stage) submitAddress.getScene().getWindow();
                stage.close();
            }
        }

        else {
            System.out.println("Error updating address");
            String message = "The address could not be updated. ";
            dialogCreator.displayErrorDialog("Error while updating address", message);
        }


    }

    public void cancelButton(ActionEvent actionEvent) {
        sceneChange.sceneChangeButton("fxml/UserMainScreenUI.fxml", cancelButton, 800, 500);

    }
}
