package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Book;


public class ShoppingCartController {

    @FXML
    TextField tf;

    private Book book;

    void setBook(Book b){
        this.book = b;
        tf.setText(book.toString());
    }

    @FXML
    public void initialize() {

    }

    public void logoutButtonPressed(ActionEvent actionEvent) {
    }
}
