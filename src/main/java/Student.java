import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private String password;
    private double fine;
    private List<Book> borrowedBooks;

    public Student(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.fine = 0;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getFine() {
        return fine;
    }
    public void removeFine(double amount) {
        if (this.fine >= amount) {
            this.fine -= amount;
        } else {
            this.fine = 0;
        }
    }
    public void addFine(double amount) {
        this.fine += amount;
    }

    public void clearFine() {
        this.fine = 0;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public boolean hasFine() {
        return fine > 0;
    }
}
