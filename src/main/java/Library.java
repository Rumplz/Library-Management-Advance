import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<LibraryItem> items;
    private List<Student> students;
    private List<Admin> admins;

    public Library(String myLibrary) {
        items = new ArrayList<>();
        students = new ArrayList<>();
        admins = new ArrayList<>();
        initializeAdmin();
    }

    public void initializeLibrary() {
        items.add(new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565"));
        items.add(new Book("B002", "1984", "George Orwell", "9780451524935"));
        items.add(new Book("B003", "To Kill a Mockingbird", "Harper Lee", "9780060935467"));
        items.add(new Book("B004", "Pride and Prejudice", "Jane Austen", "9780141439518"));
        items.add(new Book("B005", "The Catcher in the Rye", "J.D. Salinger", "9780316769488"));
        items.add(new Book("B006", "The Hobbit", "J.R.R. Tolkien", "9780547928227"));
        items.add(new Book("B007", "Moby Dick", "Herman Melville", "9781503280786"));
        items.add(new Book("B008", "The Odyssey", "Homer", "9780140268867"));
        items.add(new Book("B009", "War and Peace", "Leo Tolstoy", "9780199232765"));
        items.add(new Book("B010", "The Divine Comedy", "Dante Alighieri", "9780140448955"));
    }

    private void initializeAdmin() {
        admins.add(new Admin("admin", "admin123"));
    }

    public List<LibraryItem> getItems() {
        return items;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void removeItem(String id) {
        items.removeIf(item -> item.getId().equals(id));
    }

    public void editItem(String id, String title, String author, String isbn) {
        for (LibraryItem item : items) {
            if (item.getId().equals(id)) {
                Book book = (Book) item;
                book.setTitle(title);
                book.setAuthor(author);
                book.setIsbn(isbn);
                break;
            }
        }
    }

    public LibraryItem getItemById(String id) {
        for (LibraryItem item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student getStudent(String username) {
        for (Student student : students) {
            if (student.getId().equals(username)) {
                return student;
            }
        }
        return null;
    }

    public boolean authenticateStudent(String username, String password) {
        for (Student student : students) {
            if (student.getId().equals(username) && student.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean authenticateAdmin(String username, String password) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public Admin getAdmin(String username) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username)) {
                return admin;
            }
        }
        return null;
    }
}
