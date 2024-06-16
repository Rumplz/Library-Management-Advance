public class Book extends LibraryItem {
    private String author;
    private String isbn;

    public Book(String id, String title, String author, String isbn) {
        super(id, title);
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return super.toString() + ", Author: " + author + ", ISBN: " + isbn;
    }
}
