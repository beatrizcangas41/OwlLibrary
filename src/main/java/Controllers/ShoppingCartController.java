package Controllers;

import database.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Book;
import util.dialogCreator;
import util.sceneChange;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Book_OrderDatabaseHandler.*;
import static database.UserDatabaseHandler.getAddressFromUsername;
import static database.UserDatabaseHandler.userExists;
import static util.dialogCreator.displayErrorDialog;
import static util.sceneChange.sceneChangeButton;


public class ShoppingCartController {

    @FXML public TextField username;

    @FXML private Button submitOrder, cancel, logoutButtonPressed;

    @FXML TableView<Book> shoppingCartTableView;
    @FXML private TableColumn<Book, String> titleColumn, authorColumn, descriptionColumn;
    @FXML private TableColumn<Book, Double> priceColumn;

    private static ArrayList<Book> books;
    private UserMainScreenController userMainScreenController;
    private AddressUpdateController AddressUpdateController;

    private String address;

    void setBook(Book book){
        books.add(book);

        shoppingCartTableView.setItems(FXCollections.observableArrayList(books));
    }

    void setName(String name) {
        username.setText(name);
    }

    String getName() {
        return username.getText();
    }

    @FXML
    public void initialize() {
        if (books == null) books = new ArrayList<>();
        userMainScreenController = new UserMainScreenController();

        setupTableView();
    }

    private void setupTableView() {

        titleColumn = new TableColumn<>("Title");
        authorColumn = new TableColumn<>("Author");
        descriptionColumn = new TableColumn<>("Description");
        priceColumn = new TableColumn<>("Price");

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        titleColumn.prefWidthProperty().bind(shoppingCartTableView.widthProperty().multiply(0.15));
        authorColumn.prefWidthProperty().bind(shoppingCartTableView.widthProperty().multiply(0.15));
        descriptionColumn.prefWidthProperty().bind(shoppingCartTableView.widthProperty().multiply(0.7));
        //priceColumn.prefWidthProperty().bind(shoppingCartTableView.widthProperty().multiply(0.05));

        titleColumn.setStyle("-fx-alignment: center;");
        authorColumn.setStyle("-fx-alignment: center;");
        descriptionColumn.setStyle("-fx-alignment: left;");

        titleColumn.setStyle("-fx-alignment: center;");
        authorColumn.setStyle("-fx-alignment: center;");
        descriptionColumn.setStyle("-fx-alignment: center;");

        titleColumn.setCellFactory(param -> {
            TableCell<Book, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

        authorColumn.setCellFactory(param -> {
            TableCell<Book, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

        descriptionColumn.setCellFactory(param -> {
            TableCell<Book, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

        shoppingCartTableView.getColumns().addAll(titleColumn, authorColumn, descriptionColumn);
        shoppingCartTableView.setItems(FXCollections.observableArrayList(books));
    }

    public void logoutButtonPressed(ActionEvent actionEvent) {
        sceneChangeButton("fxml/loginScreenUI.fxml", logoutButtonPressed, 800, 500);
    }

    public void submitOrder(ActionEvent actionEvent) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Statement stmt2 = null;

        if (titleColumn.equals(null)) {
            System.out.println("no books in cart");

            String message = "There is nothing to submit! You have been redirected to the main screen.";
            dialogCreator.displayErrorDialog("No books in cart", message);
            sceneChangeButton("fxml/UserMainScreenUI.fxml", submitOrder, 1200, 800);
        }

        else {
            String uName = username.getText();
            address = getAddressFromUsername(uName);

            if (!userExists(uName) || uName.isEmpty() || uName.equals(null)) {
                String message = "The system has encountered some error regarding your username. Apologies for the inconvenience";
                dialogCreator.displayErrorDialog("no username", message);
            } else {

                ResultSet result1 = checkAddressInUserTable(uName);

                if (result1.wasNull()) {
                    System.out.println("User must enter address");
                    displayErrorDialog("Input not valid", "There is no address in the system. Please update.");

                    try {
                        // Load an instance of the menu bar and assign it to menubar
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/AddressUpdateUI.fxml"));
                        Parent parent = loader.load();

                        Scene newScene = new Scene(parent, 800, 500);
                        Stage newStage = new Stage();

                        newStage.setScene(newScene);
                        newStage.show();

                        AddressUpdateController = loader.getController();
                        AddressUpdateController.setAddress(address);
                        AddressUpdateController.setUsername(getName());

                    } catch (IOException ioEx) {
                        ioEx.printStackTrace();
                    }

                } else {
                    String message = "Please confirm Address";

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Address Confirmation");
                    alert.setContentText("Your current address is: " + address + ". Please press 'OK' to confirm. ");
                    // alert.showAndWait();

                    ButtonType buttonTypeCONFIRM = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    ButtonType buttonTypeMODIFY = new ButtonType("MODIFY");

                    alert.getButtonTypes().setAll(buttonTypeCONFIRM, buttonTypeMODIFY);
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent()) {
                        if (result.get() == buttonTypeMODIFY) {

                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/AddressUpdateUI.fxml"));
                                Parent parent = loader.load();

                                Scene newScene = new Scene(parent, 800, 500);
                                Stage newStage = new Stage();

                                newStage.setScene(newScene);
                                newStage.show();

                                AddressUpdateController = loader.getController();
                                AddressUpdateController.setAddress(address);
                                AddressUpdateController.setUsername(getName());

                            } catch (IOException ioEx) {
                                ioEx.printStackTrace();
                            }

                        }

                        else if (result.get() == buttonTypeCONFIRM) {

                            List<String> title = new ArrayList<>();
                            String bookID = getBookFromTitle(String.valueOf(title));


                            for (Book item : shoppingCartTableView.getItems()) {
                                title.add(titleColumn.getCellObservableValue(item).getValue());
                                title.subList(0, title.size());

                                addOrder(String.valueOf(title), bookID, uName, address);
                            }

                            System.out.println("uName " + uName);
                            System.out.println("address " + address);
                            System.out.println("title " + title);

                            try {
                                sceneChange.sceneChangeButton("fxml/loginScreenUI.fxml", cancel, 800, 500);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();

        try {
            // Load an instance of the menu bar and assign it to menubar
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/UserMainScreenUI.fxml"));
            Parent parent = loader.load();

            Scene newScene = new Scene(parent, 1200, 800);
            Stage newStage = new Stage();

            newStage.setScene(newScene);
            newStage.show();

            userMainScreenController = loader.getController();
            userMainScreenController.setName(getName());

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

}
