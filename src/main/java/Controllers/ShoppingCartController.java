
package Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;

import java.util.ArrayList;


public class ShoppingCartController {


    @FXML TableView<Book> shoppingCartTableView;

    @FXML private TableColumn<Book, String> titleColumn, authorColumn, descriptionColumn;
    @FXML private TableColumn<Book, Double> priceColumn;

    private static ArrayList<Book> books;

    void setBook(Book book){
        this.books.add(book);


        shoppingCartTableView.setItems(FXCollections.observableArrayList(books));
    }

    @FXML
    public void initialize() {
        if (books == null) books = new ArrayList<>();

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


        shoppingCartTableView.getColumns().addAll(titleColumn, authorColumn, descriptionColumn, priceColumn);
        shoppingCartTableView.setItems(FXCollections.observableArrayList(books));
    }

    public void logoutButtonPressed(ActionEvent actionEvent) {
    }
}
