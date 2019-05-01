package Controllers;

import database.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static database.Book_OrderDatabaseHandler.getInfoFromOrderTable;

public class OrderDetailController {

    @FXML
    public TextField username;

    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<Book, String> orderIDColumn, bookTitleColumn, orderDateColumn;

    void setName(String name) {
        username.setText(name);
    }

    String getName() {
        return username.getText();
    }

    public void submitOrder(ActionEvent actionEvent) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Statement stmt2 = null;
        String orderID = null;
        String bookTitle = null;
        String orderDate = null;


        ResultSet results = getInfoFromOrderTable(getName());
        ObservableList<Book> book = FXCollections.observableArrayList();

        /*
        while(results.next()){
            book.add(new Book(
                    results.getString("bookTitle");
            results.getDouble("orderDate");
            results.getInt("orderID")))

        }*/

        orderIDColumn = new TableColumn<>("OrderID");
        bookTitleColumn = new TableColumn<>("Title");
        orderDateColumn = new TableColumn<>("Ordered Date");

        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        orderIDColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));
        bookTitleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        orderDateColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));

        tableView.getColumns().addAll(orderIDColumn, bookTitleColumn, orderDateColumn);
        tableView.setItems(book);
    }
}
