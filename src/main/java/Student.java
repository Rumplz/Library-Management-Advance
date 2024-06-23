import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String id;
    private String password;
    private double fine;
    private List<Book> borrowedBooks;

    public Student(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.fine = 0.0;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public double getFine() {
        return fine;
    }

    public void addFine(double fine) {
        this.fine += fine;
    }

    public void removeFine(double fine) {
        this.fine = Math.max(this.fine - fine, 0);
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        if (borrowedBooks.contains(book)) {
            throw new IllegalStateException("You already have this book.");
        }
        borrowedBooks.add(book);
        book.setAvailable(false);
    }

    public void returnBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            throw new IllegalStateException("You don't have this book.");
        }
        borrowedBooks.remove(book);
        book.setAvailable(true);
    }
}
