import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;
    private List<LibraryItem> items;
    private List<Student> students;

    public Library(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void removeItem(String id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    public LibraryItem searchItemById(String id) {
        return items.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    }

    public void editItem(String id, String title, String author, String isbn) {
        LibraryItem item = searchItemById(id);
        if (item instanceof Book) {
            Book book = (Book) item;
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
        }
    }

    public List<LibraryItem> getItems() {
        return items;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }
}
