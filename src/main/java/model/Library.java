package model;

import java.util.ArrayList;
import java.util.List;

public class Library extends Book
{
    private String libraryName;
    private String libraryID;
    public List<Book> libraryBooks = new ArrayList<>();
    
    
    public Library()
    {
        this.libraryName = null;
        this.libraryID = null;
    }

    public Library(String bLibraryName, String ID)
    {
        this.libraryName = bLibraryName;
        this.libraryID = ID;
    }

    public String getLibraryName()
    {
        return this.libraryName;
    }
    
    public String getID(String name)
    {
        if(this.libraryName.equals(name))
        {
            return this.libraryID;
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public String toString()
    {
        return "Location: " + this.libraryName + "\t\t\tID: " + this.libraryID;
    }
    
    private boolean checkAvailability(List Book)
    {
        model.Book b = new Book();

        if(Book.size() != 0)
        {
            return b.availability;
        }
        else
        {
            b.availability = false;
            return b.availability;
        }
    }
}
