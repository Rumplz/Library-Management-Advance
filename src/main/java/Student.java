import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String id;
    private String password;
    private List<Book> borrowedBooks;
    private double fine;

    public Student(String name, String id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
        this.fine = 0;
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

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public double getFine() {
        return fine;
    }

    public void addFine(double amount) {
        fine += amount;
    }

    public void removeFine(double amount) {
        fine -= amount;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}
