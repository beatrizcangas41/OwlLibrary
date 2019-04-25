package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Book;
import model.Cart;
import model.Library;

import java.io.IOException;
import java.util.ArrayList;

public class Display extends Application {

    private ArrayList<Book> books = new ArrayList<>();
    //public LinkedList<Book> books = new LinkedList<Book>();
    public ArrayList<Library> lib = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        try {
            Object page = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/loginScreenUI.fxml"));
            Scene scene = new Scene((Parent) page, 800, 400);
            primaryStage.setTitle("Owl Library");

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Display() {
        int show = 0;

    }

    public void addBookToList(Book book)
    {
        books.add(book);
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

            else if (book.getSeries().contains(title)) {
                System.out.println("Series: " + book.getSeries() + "\t\t\tSeries from String: " + title);
                System.out.println("\n" + book.toString());
            }

            else {
                System.out.println("Action not completed...");
            }
        }

    }

    public void displayListOfBooks() {

        System.out.println("\n\n\nBook in List: ");
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

    public void displayBooksPerLibrary() {
        System.out.println("Book Per Library");
        for (model.Library libraries : lib) {
            System.out.println("\n" + libraries.toString() + "\n\t");
            for (Book book : books) {
                book.setLibrary(libraries.toString());
                //books.add(books.get(a));
                System.out.println(book.toString());
            }
        }
    }

    public void displayLibraries() {
        System.out.println("Library in List: \n");
        for (model.Library libraries : lib) {
            System.out.println(libraries.toString());
        }
    }

    public void removeBook(String rent) {
        for (Book book : books) {
            if (book.getTitle().contains(rent)) {
                System.out.println(book.toString());
                if (book.getQuantity() != 0) {
                    book.setQuantity(1);
                } else {
                    book.availability = false;
                }
            } else if (book.getAuthor().contains(rent)) {
                System.out.println("Author: " + book.getAuthor() + "\t\t\tAuthor from String: " + rent);
                System.out.println("\n" + book.toString());
                if (book.getQuantity() != 0) {
                    book.setQuantity(1);
                } else {
                    book.availability = false;
                }
            } else if (book.getSeries().contains(rent)) {
                System.out.println("Series: " + book.getSeries() + "\t\t\tSeries from String: " + rent);
                System.out.println("\n" + book.toString());
                if (book.getQuantity() != 0) {
                    book.setQuantity(1);
                } else {
                    book.availability = false;
                }
            } else {
                System.out.println();
            }
        }


    }

    public void displayBooksInCart(Cart cart)
    {
        cart.toString();
    }

    public void displayOrderSummary() {
        //displayBooksInCart();
    }

    public void displayShippingInfo() {
        //hello
    }

    public void displayReceipt() {
        //hello
    }

    public static void main(String[] args) {
        launch(args);
    }
}