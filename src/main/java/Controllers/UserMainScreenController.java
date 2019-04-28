package Controllers;

import database.BookDatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Book;
import util.sceneChange;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMainScreenController {

    @FXML private TextField searchBar;
    @FXML public MenuButton menuButton, menuDropdownButton;
    @FXML private Button searchButton, logoutButtonPressed, SearhButton;
    @FXML private ComboBox filterTypeComBoBox, menuComboBox;

    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<Book, String> titleColumn, authorColumn, descriptionColumn;
    @FXML private TableColumn<Book, Double> priceColumn;
    @FXML private TableColumn<Book, Integer> quantityColumn;
    @FXML private TableColumn<Book, Void> actionColumn;

    @FXML
    public void initialize() throws SQLException {
        String[] filterValues = {"Title", "Author", "Description", "Price"};
        filterTypeComBoBox.setItems(FXCollections.observableArrayList(filterValues));
        setupTableView();
    }

    public void logout(ActionEvent actionEvent) {
        sceneChange.sceneChangeButton("fxml/loginScreenUI.fxml", logoutButtonPressed, 1200, 800);
    }

    private void openShoppingCart(Book book){
        try {
            // Load an instance of the menu bar and assign it to menubar
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ShoppingCartUI.fxml"));
            Parent parent = loader.load();

            Scene newScene = new Scene(parent, 1000, 1000);
            Stage newStage = new Stage();

            newStage.setScene(newScene);
            newStage.show();

            ShoppingCartController shoppingCartController = loader.getController();
            shoppingCartController.setBook(book);

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private void setupTableView() throws SQLException {

        ResultSet resultSet = BookDatabaseHandler.getBooks();
        ObservableList<Book> book = FXCollections.observableArrayList();

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

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        titleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        authorColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        descriptionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.6));
        priceColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        quantityColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        actionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.08));

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
                                        openShoppingCart(book1);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                    }
                };
        col_action.setCellFactory(cellFactory);

        titleColumn.setStyle("-fx-alignment: center;");
        authorColumn.setStyle("-fx-alignment: center;");
        descriptionColumn.setStyle("-fx-alignment: left;");
        priceColumn.setStyle("-fx-alignment: center;");
        quantityColumn.setStyle("-fx-alignment: center;");
        actionColumn.setStyle("-fx-alignment: center;");

        titleColumn.setStyle("-fx-alignment: center;");
        authorColumn.setStyle("-fx-alignment: center;");
        descriptionColumn.setStyle("-fx-alignment: center;");
        priceColumn.setStyle("-fx-alignment: center;");
        quantityColumn.setStyle("-fx-alignment: center;");
        actionColumn.setStyle("-fx-alignment: center;");

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

        tableView.getColumns().addAll(titleColumn, authorColumn, descriptionColumn, priceColumn, actionColumn);
        tableView.setItems(book);

        FilteredList<Book> flBook = new FilteredList(book, p -> true);//Pass the data to a filtered list
        tableView.setItems(flBook);//Set the table's items using the filtered list

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> flBook.setPredicate(Book -> {
            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            // Get filter type from combobox
            String filterType = filterTypeComBoBox.getSelectionModel().isEmpty() ? "Author" : filterTypeComBoBox.getValue().toString();
            System.out.println(filterType);

            // Compare first name and last name field in your object with filter.
            String lowerCaseFilter = newValue.toLowerCase();

            switch (filterType){
                case "Author":
                    return Book.getAuthor().toLowerCase().contains(lowerCaseFilter);
                case "Price":
                    return Book.getPrice().toString().contains(lowerCaseFilter);
                case "Description":
                    return Book.getDescription().contains(lowerCaseFilter);
                case "Title":
                    return Book.getTitle().contains(lowerCaseFilter);
            }
            return false;
        }));

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Book> sortedData = new SortedList<>(flBook);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        tableView.setItems(sortedData);

        filterTypeComBoBox.setButtonCell(new ListCell(){

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item==null){
                    setStyle("-fx-font-size:15");
                    setStyle("-fx-font-family: 'Segoe UI Bold'");
                    setStyle("-fx-alignment: 'center'");
                } else {
                    setStyle("-fx-font-size:15");
                    setText(item.toString());
                }
            }

        });

    }

    public void logoutButtonPressed(ActionEvent actionEvent) {
    }
}
