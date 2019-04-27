package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {

        private static int quantity;
        public static boolean availability = true;

        private SimpleIntegerProperty bookID = new SimpleIntegerProperty();
        private String title, author;
        private String description = String.valueOf(new SimpleStringProperty());
        private String library = String.valueOf(new SimpleStringProperty());
        private Double price;

        Book(String title, String author, String description, double price) {
                bookID = null;
                this.title = null;
                this.author = null;
                this.description = null;
                this.price = null;
                quantity = 0;
        }

        Book() {
                this.title = null;
                this.author = null;
                this.description = null;
                this.price = null;
                quantity = 0;
        }

        public Book(String bTitle, String bAuthor, String bDescription, Double bPrice) {
                this.author = bAuthor;
                this.title = bTitle;
                this.description = bDescription;
                this.price = bPrice;
                quantity = 3;
        }

        public Book(String title, String author) {
                this.title = title;
                this.author = author;
        }

        public static boolean isAvailability() {
                return availability;
        }

        public void setAvailability(boolean availability) {
                Book.availability = availability;
        }

        public int getBookID() {
                return bookID.get();
        }

        public SimpleIntegerProperty bookIDProperty() {
                return bookID;
        }

        public void setBookID(int bookID) {
                this.bookID.set(bookID);
        }

        public String getDescription() {
                return description;
        }

        public String getLibrary() {
                return library;
        }

        public String getTitle() {
                return this.title;
        }

        public String getAuthor() {
                return this.author;
        }


        public static int getQuantity() {
                return quantity;
        }

        public Double getPrice() {
                return this.price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public void setLibrary(String name) {
                this.library = name;
        }

        public void setQuantity(int value) {
                quantity = quantity - value;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        private boolean availability() {
                return availability;
        }

        @Override
        public String toString() {
                return "\nTitle: " + this.title + "\nAuthor: " + this.author + "\t\t\t\t\t\t\tPrice: " + this.price +
                        "\nDescription: " + this.description  + "\nLocation: " + this.library + "\nAvailability: " +
                        availability() + "\t\t\t\tCount: " + quantity;
        }
}

