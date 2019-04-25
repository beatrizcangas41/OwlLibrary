package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Book;

import java.io.IOException;

public class UserMainScreenController {

    private TableColumn<Book, String> titleColumn;
    private TableColumn<Book, String> authorColumn;
    private TableColumn<Book, String> publishDateColumn;
    @FXML
    TableView<Book> tableView;

    @FXML
    public void initialize() {
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

    private void setupTableView() {
        // sample
        ObservableList<Book> books = FXCollections.observableArrayList(
                new Book("Book Title 1", "Book Author 1", "Comedy", 50),
                new Book("Book Title 2", "Book Author 2", "Comedy", 50),
                new Book("Book Title 3", "Book Author 3", "Comedy", 50),
                new Book("Book Title 4", "Book Author 4", "Comedy", 50),
                new Book("Book Title 5", "Book Author 5", "Comedy", 50),
                new Book("Book Title 6", "Book Author 6", "Comedy", 50));

        titleColumn = new TableColumn<>("Title");
        authorColumn = new TableColumn<>("Author");
        publishDateColumn = new TableColumn<>("Date");

        titleColumn.setCellValueFactory((new PropertyValueFactory<>("title")));
        authorColumn.setCellValueFactory((new PropertyValueFactory<>("author")));
        publishDateColumn.setCellValueFactory((new PropertyValueFactory<>("publishDate")));


        // titleColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
        authorColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
        publishDateColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));


        tableView.getColumns().addAll(titleColumn, authorColumn, publishDateColumn);

        tableView.setItems(books);
    }


}
