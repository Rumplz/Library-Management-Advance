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
        items.add(new Book("1", "The Great Gatsby", "F. Scott Fitzgerald", "10"));
        items.add(new Book("2", "1984", "George Orwell", "11"));
        items.add(new Book("3", "To Kill a Mockingbird", "Harper Lee", "12"));
        items.add(new Book("4", "Pride and Prejudice", "Jane Austen", "13"));
        items.add(new Book("5", "The Catcher in the Rye", "J.D. Salinger", "14"));
        items.add(new Book("6", "The Hobbit", "J.R.R. Tolkien", "15"));
        items.add(new Book("7", "Moby Dick", "Herman Melville", "16"));
        items.add(new Book("8", "The Odyssey", "Homer", "17"));
        items.add(new Book("9", "War and Peace", "Leo Tolstoy", "18"));
        items.add(new Book("10", "The Divine Comedy", "Dante Alighieri", "19"));
    }

    private void initializeAdmin() {
        admins.add(new Admin("admin", "admin123"));
        admins.add(new Admin("1", "1"));
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
