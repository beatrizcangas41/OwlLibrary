package Controllers;

import database.BookDatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Book;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMainScreenController {

    @FXML private TableView<Book> tableView;

    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> descriptionColumn;
    @FXML private TableColumn<Book, Double> priceColumn;
    @FXML private TableColumn<Book, Integer> quantityColumn;

    @FXML private Button addButton, addRemove, addUpdate;
    @FXML private Button goButton;
    @FXML private Button bookButton;

    @FXML
    public void initialize() throws SQLException {
        setupTableView();
    }

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

    private void setupTableView() throws SQLException {
        ResultSet resultSet = BookDatabaseHandler.getBooks();
        ObservableList<Book> book = FXCollections.observableArrayList();

        String title, author, description;
        double price;

        while(resultSet.next()){
            book.add(new Book(
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price")));
        }

        titleColumn = new TableColumn<>("Title");
        authorColumn = new TableColumn<>("Author");
        descriptionColumn = new TableColumn<>("Description");
        priceColumn = new TableColumn<>("Price");
        quantityColumn = new TableColumn<>("Quantity");

        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory("author"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory("quantity"));


        titleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        authorColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        descriptionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        priceColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        quantityColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));

        TableColumn<Book, Void> actionColumn = new TableColumn<>("Action");

        actionColumn.setCellFactory(param -> new TableCell<Book, Void>() {
            private final Button editButton = new Button("edit");
            private final Button deleteButton = new Button("delete");
            private final HBox pane = new HBox(deleteButton, editButton);

            {
                deleteButton.setOnAction(event -> {
                    Book book1 = getTableView().getItems().get(getIndex());
                    System.out.println(book1.getQuantity() + "   " + book1.isAvailability());
                });

                editButton.setOnAction(event -> {
                    Book getPatient = getTableView().getItems().get(getIndex());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : pane);
            }
        });



        tableView.getColumns().addAll(titleColumn, authorColumn, descriptionColumn, priceColumn, actionColumn);
        tableView.setItems(book);

    }
}
