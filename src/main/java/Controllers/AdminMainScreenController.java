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
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Book;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMainScreenController {

    @FXML private TableView<Book> tableView;

    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> descriptionColumn;
    @FXML private TableColumn<Book, Double> priceColumn;
    @FXML private TableColumn<Book, Integer> quantityColumn;
    @FXML private TableColumn<Book, Void> actionColumn;

    @FXML private Button addButton;

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
        actionColumn = new TableColumn<>("Action");

        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory("author"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory("quantity"));

        TableColumn col_action = actionColumn;
        Callback<TableColumn<Book, String>, TableCell<Book, String>> cellFactory =
                new Callback<TableColumn<Book, String>, TableCell<Book, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Book, String> param) {
                        return new TableCell<Book, String>() {

                            final Button btn = new Button("Add to Cart");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Book book1 = getTableView().getItems().get(getIndex());
                                        System.out.println(book1.getTitle()
                                                + "   " + book1.getAuthor());
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                    }
                };
        col_action.setCellFactory(cellFactory);

        titleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.185));
        authorColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        descriptionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        priceColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        quantityColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        actionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));

        priceColumn.setStyle("-fx-alignment: center;");
        quantityColumn.setStyle("-fx-alignment: center;");
        actionColumn.setStyle("-fx-alignment: center;");

        tableView.getColumns().addAll(titleColumn, authorColumn, descriptionColumn, priceColumn, quantityColumn, actionColumn);
        tableView.setItems(book);

    }
}
