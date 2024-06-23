public class Book extends LibraryItem {
    private String author;
    private String isbn;
    private boolean available;

    public Book(String id, String title, String author, String isbn) {
        super(id, title);
        this.author = author;
        this.isbn = isbn;
        this.available = true;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
