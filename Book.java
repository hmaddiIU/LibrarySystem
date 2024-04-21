/*
 * Project: Library System
 * Author     : Belmir Husejinovic
 * Created on : 4/7/24
 * Updated by : Hamid Maddi
 * Updated on : 04/21/24
 * Description: This class will be used to represent a book in the library system.
 */
import java.io.Serializable;

public class Book implements Serializable {
    // Attributes
    private static final long serialVersionUID = 2L;
    private int bookId;
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;

    // Default constructor
    public Book() {
    }

    public Book(int bookId, String title, String author, String ISBN, boolean isAvailable) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
    }

    // Returns book ID
    public int getBookId() {
        return bookId;
    }

    // Updates book ID
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    // Returns book title
    public String getTitle() {
        return title;
    }

    // Updates book title
    public void setTitle(String title) {
        this.title = title;
    }

    // Returns book author
    public String getAuthor() {
        return author;
    }

    // Updates book author
    public void setAuthor(String author) {
        this.author = author;
    }

    // Returns ISBN
    public String getISBN() {
        return ISBN;
    }

    // Updates ISBN
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    // Checks if book is available
    public boolean isAvailable() {
        return isAvailable;
    }

    // Updates availability 
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
