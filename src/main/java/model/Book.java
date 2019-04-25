package model;

public class Book {

        // instance variables - replace the example below with your own
        private String title, author, series;
        private double price;
        private int quantity;
        private String description, Availability, library;
        public boolean availability = true;

        public Book() {
                // initialise instance variables
                title = null;
                author = null;
                series = null;
                description = null;
                price = 0;
                quantity = 0;
        }

        public Book(String title)
        {
                this.title = title;
                this.author = null;
                this.series = null;
                this.description = null;
                this.price = 0;
        }

        public Book(String bTitle, String bAuthor, String bDescription, double bPrice) {
                this.title = bTitle;
                this.author = bAuthor;
                this.price = bPrice;
                this.quantity = 3;
                this.description = bDescription;
        }

        public Book(String bTitle, String bAuthor, String bDescription, String bSeries, double bPrice) {
                this.title = bTitle;
                this.author = bAuthor;
                this.series = bSeries;
                this.description = bDescription;
                this.price = bPrice;
                this.quantity = 3;
        }
        public Book(String title, String author) {
                this.title = title;
                this.author = author;
        }

        public void setTitle(String title) {
                this.title = title;
        }


        public void setAuthor(String author) {
                this.author = author;
        }


        public String getTitle() {
                return this.title;
        }

        public String  getAuthor() {
                return this.author;
        }

        public String getSeries() {
                return this.series;
        }

        public String getLibrary() {
                return this.library;
        }
        public int getQuantity() {
                return this.quantity;
        }

        public double getPrice() {
                return this.price;
        }

        public void setLibrary(String name) {
                this.library = name;
        }

        public void setQuantity(int value) {
                this.quantity = this.quantity - value;
        }

        private boolean availability() {
                return this.availability;
        }

        @Override
        public String toString() {
                return "\nTitle: " + this.title + "\nAuthor: " + this.author + "\t\t\t\t\t\t\tPrice: " + this.price + "\t\t\t\t\tSeries: "+
                        this.series +"\nDescription: " + this.description  + "\nLocation: " + this.library + "\nAvailability: " +
                        availability() + "\t\t\t\tCount: " + this.quantity;
        }
}

