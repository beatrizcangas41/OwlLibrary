package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Book;
import model.Library;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

public class Display extends Application {

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    public ArrayList<Library> lib = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScreenUI.fxml"));
        Scene scene = new Scene(page, 800, 500);
        Stage stage = new Stage();
        stage.setTitle("Owl Library");

        stage.setScene(scene);
        stage.show();
    }

    public void addBookToList(Book book) {
        books.add(book);
    }

    public void removeBook(String rent) {
        for (Book book : books) {
            if (book.getTitle().contains(rent)) {
                System.out.println(book.toString());
                if (Book.getQuantity() != 0) {
                    book.setQuantity(1);
                } else {
                    Book.availability = false;
                }
            } else if (book.getAuthor().contains(rent)) {
                System.out.println("Author: " + book.getAuthor() + "\t\t\tAuthor from String: " + rent);
                System.out.println("\n" + book.toString());
                if (Book.getQuantity() != 0) {
                    book.setQuantity(1);
                } else {
                    Book.availability = false;
                }
            }

            else System.out.println();
        }
    }

    public void displayBookInfo(String title) {

        for (Book book : books) {

            if (book.getTitle().contains(title)) {
                System.out.println(book.toString());
            }

            else if (book.getAuthor().contains(title)) {
                System.out.println("Author: " + book.getAuthor() + "\t\t\tAuthor from String: " + title);
                System.out.println("\n" + book.toString());
            }

            else System.out.println("Action not completed...");
        }
    }

    public void displayListOfBooks() {

        System.out.println("\n\n\nBook in List: ");
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }


    public void displayLibraries() {
        System.out.println("Library in List: \n");
        for (model.Library libraries : lib) {
            System.out.println(libraries.toString());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}