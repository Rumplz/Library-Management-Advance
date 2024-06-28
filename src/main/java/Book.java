import java.time.LocalDate;
////
public class Book extends LibraryItem {
    private String title;
    private String author;
    private String isbn;
    private boolean borrowed;
    private String borrowedBy;
    private LocalDate borrowedDate;

    public Book(String id, String title, String author, String isbn) {
        super(id);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.borrowed = false;
        this.borrowedBy = null;
    }

    public void borrow(String studentId) {
        this.borrowed = true;
        this.borrowedBy = studentId;
        this.borrowedDate = LocalDate.now();
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void returnBook() {
        this.borrowed = false;
        this.borrowedBy = null;
        this.borrowedDate = null;
    }
}  ////