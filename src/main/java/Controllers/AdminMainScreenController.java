package Controllers;

import database.BookDatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Book;
import util.sceneChange;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMainScreenController {

    @FXML private MenuButton menuButton;
    @FXML private TableView<Book> tableView;
    @FXML public MenuItem logoutButtonPressed;

    @FXML private TableColumn<Book, String> titleColumn, authorColumn, descriptionColumn;
    @FXML private TableColumn<Book, Double> priceColumn;
    @FXML private TableColumn<Book, Integer> quantityColumn;
    @FXML private TableColumn<Book, Void> actionColumn;

    @FXML
    public void initialize() throws SQLException {
        setupTableView();
    }

    public void logoutButtonPressed(ActionEvent actionEvent) {
        // go to normal page
        sceneChange.sceneChangeMenuButton("fxml/loginScreenUI.fxml", menuButton, 1200, 800);
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

        titleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.115));
        authorColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.115));
        descriptionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.6));
        priceColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        quantityColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        actionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));

        titleColumn.setStyle("-fx-alignment: center;");
        authorColumn.setStyle("-fx-alignment: center;");
        descriptionColumn.setStyle("-fx-alignment: left;");
        priceColumn.setStyle("-fx-alignment: center;");
        quantityColumn.setStyle("-fx-alignment: center;");
        actionColumn.setStyle("-fx-alignment: center;");

        titleColumn.setStyle("-fx-alignment: center;");
        authorColumn.setStyle("-fx-alignment: center;");
        //descriptionColumn.setStyle("-fx-alignment: center;");
        priceColumn.setStyle("-fx-alignment: center;");
        quantityColumn.setStyle("-fx-alignment: center;");
        actionColumn.setStyle("-fx-alignment: center;");

        tableView.getColumns().addAll(titleColumn, authorColumn, descriptionColumn, priceColumn, quantityColumn, actionColumn);
        tableView.setItems(book);

    }
}
