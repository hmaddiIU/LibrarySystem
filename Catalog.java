import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Catalog implements Serializable {
    private ArrayList<Book> books;

    public Catalog() {
        books = new ArrayList<>();
        loadBooks();
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    public List<Book> searchBook(String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(keyword) ||
                        book.getAuthor().equalsIgnoreCase(keyword) ||
                        book.getISBN().equalsIgnoreCase(keyword))
                .collect(Collectors.toList());
    }

    public void removeBook(int bookId) {
        books.removeIf(book -> book.getBookId() == bookId);
        saveBooks();
    }

    public ArrayList<Book> getBooks() {
        return books; 
    }

    private void loadBooks() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("books.ser"))) {
            books = (ArrayList<Book>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            books = new ArrayList<>(); 
        }
    }

    private void saveBooks() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("books.ser"))) {
            out.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
