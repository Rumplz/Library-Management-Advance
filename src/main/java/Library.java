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

       //data base for books
        items.add(new Book("1", "Book 1", "Author 1", "ISBN1"));
        items.add(new Book("2", "Book 2", "Author 2", "ISBN2"));
        items.add(new Book("3", "Book 3", "Author 3", "ISBN3"));
        items.add(new Magazine("4", "Magazine 1", "Publisher 1"));
        items.add(new Magazine("5", "Magazine 2", "Publisher 2"));
// students
        students.add(new Student("Student 1", "Mujtaba", "mujtaba"));
        students.add(new Student("Student 2", "student2", "password2"));
        students.add(new Student("Student 3", "student3", "password3"));

    }

    public String getName() {
        return name;
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

    public void removeItem(String itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
    }

    public void editItem(String itemId, String newTitle) {
        for (LibraryItem item : items) {
            if (item.getId().equals(itemId)) {
                item.setTitle(newTitle);
                break;
            }
        }
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addFine(String studentId, double fine) {
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                student.addFine(fine);
                break;
            }
        }
    }

    public void removeFine(String studentId, double fine) {
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                student.removeFine(fine);
                break;
            }
        }
    }
}
