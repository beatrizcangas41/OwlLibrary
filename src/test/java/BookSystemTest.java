import main.java.Display;
import model.Book;
import model.Library;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BookSystemTest {
    private static Book book;
    private static Display show;
    static String BookInfo;

    public static void main(String[] args) {
        //Books book = new Books();
        Library lib = new Library();

        show = new Display();
        Scanner scan = new Scanner(System.in);
        int optionA;
        String title;
        String optionB = null;

        LoadBookFile();

        System.out.println("Welcome to the OwlLibrary, 'A Place of fun and Adventure in the shape of knowledge'");
        System.out.println("\nPlease Select and option below to begin using the library");
        System.out.println("\n\nPress 1 to display list of Books");
        System.out.println("\nPress 2 to search for a book");
        System.out.println("\npress 3 to display library list");
        System.out.println("\nPress 4 to see list of books per library");
        System.out.println("\nPress 5 to Rent or Purchase a book");
        System.out.println("\nPress 6 to check if Book is available");
        System.out.println("\nPress 7 to LogOut");
        optionA = scan.nextInt();

        while((optionA != 7))
        {
            System.out.println("Welcome to the OwlLibrary, 'A Place of fun and Adventure in the shape of knowledge'");
            System.out.println("\nPlease Select and option below to begin using the library");
            System.out.println("\n\nPress 1 to display list of Books");
            System.out.println("\nPress 2 to search for a book");
            System.out.println("\npress 3 to display library list");
            System.out.println("\nPress 4 to see list of books per library");
            System.out.println("\nPress 5 to Rent or Purchase a book");
            System.out.println("\nPress 6 to check if Book is available");
            System.out.println("\nPress 7 to LogOut");
            optionA = scan.nextInt();

            switch (optionA) {
                case 1: show.displayListOfBooks();
                    break;

                case 2:
                    System.out.print("\nPlease Enter Book's Title or Author's Name or Series if any: ");
                    title = scan.next();
                    show.displayBookInfo(title);
                    break;

                case 3: show.displayLibraries();
                    break;

                // case 4: show.displayBooksPerLibrary();
                    // break;

                case 5:
                    System.out.println("Type R to Rent Book or P to purchase it");
                    title = scan.next();
                    if (title.contains("r") || title.contains("R") || title.contains("p") || title.contains("P")) {
                        System.out.println("\nPlease Search Book");
                        title = scan.next();
                        show.removeBook(title);
                        show.displayListOfBooks();
                    }
                    break;

                case 6: System.out.println("Under Construction");
            }
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder")
    private static void LoadBookFile() {
        FileReader fis;
        ObjectInput in = null;
        File file = new File("Harry_Potter_Series.txt");
        String BookTitle;
        String Author;
        String Description;
        String Series;
        String Price;
        String LibName;
        String LibID;
        double price;

        if (file.exists()) {
            try {
                fis = new FileReader(file);
                BufferedReader br = new BufferedReader(fis);
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                ArrayList<String> out = new ArrayList<> ();
                ArrayList<String> lines = new ArrayList<> ();
                ArrayList<String> splitL = new ArrayList<> ();

                while (line != null) {

                    if(line == null) {
                        System.out.println("line: " + line);
                    }

                    else {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                        lines.add(line);
                    }
                }

                for (int i = 0; i < (lines.size() - 1); i++)
                {
                    System.out.println("\n\nFileLines: " + lines.get(i));
                    String[] inner = lines.get(i).split(" ");
                    splitL.addAll(Arrays.asList(inner).subList(0, 5));
                }

                for (int b = 0; b < splitL.size(); b++) {
                    BookTitle = splitL.get(b);
                    Author = splitL.get(b + 1);
                    Description = splitL.get(b + 2);
                    // Series = splitL.get(b + 3);
                    Price = splitL.get(b + 4);
                    price = Double.parseDouble(Price);
                    book = new Book(BookTitle, Author, Description, price);
                    show.addBookToList(book);
                    //System.out.println("\n\nBook Title: " + BookTitle + "\nAuthor: " + Author + "\nDescription: " + Description + "\nSeries: " + Series + "\nPrice: " + price + "\n\n");
                    b = b + 4;
                    //System.out.println("\n\nString per line: " + splitL.get(b));
                }

                out.add("NorthWest Regional Library");
                out.add("NWRL");
                out.add("Century Plaza Library");
                out.add("CPL");
                out.add("Florida Atlantic Library");
                out.add("FAL");
                out.add("Central Regional Library");
                out.add("CRL");
                out.add("Library of Congress");
                out.add("LOC");

                for (int i = 0; i < out.size(); i++) {
                    LibName = out.get(i);
                    LibID = out.get(i + 1);
                    i = i + 1;

                    Library library = new Library(LibName, LibID);
                    show.lib.add(library);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Sorry Still under construction. jijiiijijijiji");
        }
    }
}