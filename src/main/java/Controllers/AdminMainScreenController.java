package Controllers;

import database.Book_OrderDatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import util.sceneChange;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMainScreenController {
    @FXML private MenuButton menuButton;
    @FXML public MenuItem addBook, removeBook, viewListOfBooks, addUserAdmin, setUserAdmin, logout;

    @FXML  public Button usernameButton;

    @FXML public TextField username;
    @FXML public ComboBox filterTypeComBoBox;
    @FXML public TextField searchBar;

    @FXML private TableView<Book> tableView;

    @FXML private TableColumn<Book, String> titleColumn, authorColumn, descriptionColumn;
    @FXML private TableColumn<Book, Double> priceColumn;
    @FXML private TableColumn<Book, Integer> quantityColumn;

    @FXML
    public void initialize() throws SQLException {
        String[] filterValues = {"Title", "Author", "Description", "Price"};
        filterTypeComBoBox.setItems(FXCollections.observableArrayList(filterValues));
        setupTableView();
    }

    private void setupTableView() throws SQLException {
        ResultSet resultSet = Book_OrderDatabaseHandler.getBooks();
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

        titleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.13));
        authorColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        descriptionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.6));
        priceColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        quantityColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));

        titleColumn.setStyle("-fx-alignment: center;");
        authorColumn.setStyle("-fx-alignment: center;");
        descriptionColumn.setStyle("-fx-alignment: left;");
        priceColumn.setStyle("-fx-alignment: center;");
        quantityColumn.setStyle("-fx-alignment: center;");

        titleColumn.setStyle("-fx-alignment: center;");
        authorColumn.setStyle("-fx-alignment: center;");
        //descriptionColumn.setStyle("-fx-alignment: center;");
        priceColumn.setStyle("-fx-alignment: center;");
        quantityColumn.setStyle("-fx-alignment: center;");

        tableView.getColumns().addAll(titleColumn, authorColumn, descriptionColumn, priceColumn, quantityColumn);
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

        SortedList<Book> sortedData = new SortedList<>(flBook);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);

        filterTypeComBoBox.setButtonCell(new ListCell() {

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item==null){
                    setStyle("-fx-font-size:15");
                    setStyle("-fx-font-family: 'Segoe UI Bold'");
                } else {
                    setStyle("-fx-font-size:15");
                    setText(item.toString());
                }
            }

        });
    }

    public void addBook(ActionEvent actionEvent) {
        sceneChange.sceneChangeMenuButton("fxml/addBookUI.fxml", menuButton, 800, 500);
    }

    public void removeBook(ActionEvent actionEvent) {
        sceneChange.sceneChangeMenuButton("fxml/removeBookUI.fxml", menuButton, 800, 500);

    }

    public void addUserAdmin(ActionEvent actionEvent) {
        sceneChange.sceneChangeMenuButton("fxml/addUserAdminUI.fxml", menuButton, 800, 500);

    }

    public void logout(ActionEvent actionEvent) {
        sceneChange.sceneChangeMenuButton("fxml/loginScreenUI.fxml", menuButton, 800, 500);
    }
}
