import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Library library = new Library("My Library");
    private Student currentStudent = null;

    @Override
    public void start(Stage primaryStage) {
        //book ki inventory
        library.addItem(new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565"));
        library.addItem(new Book("B002", "1984", "George Orwell", "9780451524935"));
        library.addItem(new Book("B003", "To Kill a Mockingbird", "Harper Lee", "9780060935467"));
        library.addItem(new Book("B004", "Pride and Prejudice", "Jane Austen", "9780141439518"));
        library.addItem(new Book("B005", "The Catcher in the Rye", "J.D. Salinger", "9780316769488"));
        library.addItem(new Book("B006", "The Hobbit", "J.R.R. Tolkien", "9780547928227"));
        library.addItem(new Book("B007", "Moby Dick", "Herman Melville", "9781503280786"));
        library.addItem(new Book("B008", "The Odyssey", "Homer", "9780140268867"));
        library.addItem(new Book("B009", "War and Peace", "Leo Tolstoy", "9780199232765"));
        library.addItem(new Book("B010", "The Divine Comedy", "Dante Alighieri", "9780140448955"));

        // datra base for student
        library.addStudent(new Student("Alice", "S001", "student1"));
        library.addStudent(new Student("Bob", "S002", "student2"));

        showLoginPage(primaryStage);
    }

    private void showLoginPage(Stage stage) {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        TextField userField = new TextField();
        PasswordField passField = new PasswordField();
        Button loginButton = new Button("Login");

        pane.getChildren().addAll(new Label("Username:"), userField, new Label("Password:"), passField, loginButton);

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();

            if (username.equals("admin") && password.equals("admin")) {
                showAdminPanel(stage);
            } else if (username.equals("accounts") && password.equals("accounts")) {
                showAccountsPanel(stage);
            } else {
                Student student = library.getStudents().stream()
                        .filter(s -> s.getId().equals(username) && s.getPassword().equals(password))
                        .findFirst()
                        .orElse(null);

                if (student != null) {
                    currentStudent = student;
                    showStudentPanel(stage);
                } else {
                    showAlert("Login Failed", "Invalid username or password.");
                }
            }
        });

        Scene scene = new Scene(pane, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Library Management System - Login");
        stage.show();
    }

    private void showAdminPanel(Stage stage) {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Button addButton = new Button("Add Book");
        Button removeButton = new Button("Remove Book");
        Button editButton = new Button("Edit Book");
        Button viewButton = new Button("View Books");
        Button addStudentButton = new Button("Add Student");
        Button addFineButton = new Button("Add Fine");
        Button removeFineButton = new Button("Remove Fine");
        Button viewFinesButton = new Button("View All Fines");
        Button viewBorrowedBooksButton = new Button("View All Borrowed Books");
        Button logoutButton = new Button("Logout");

        pane.getChildren().addAll(addButton, removeButton, editButton, viewButton, addStudentButton, addFineButton, removeFineButton, viewFinesButton, viewBorrowedBooksButton, logoutButton);

        addButton.setOnAction(e -> addBook());
        removeButton.setOnAction(e -> removeBook());
        editButton.setOnAction(e -> editBook());
        viewButton.setOnAction(e -> viewBooks());
        addStudentButton.setOnAction(e -> addStudent());
        addFineButton.setOnAction(e -> addFine());
        removeFineButton.setOnAction(e -> removeFine());
        viewFinesButton.setOnAction(e -> viewAllFines());
        viewBorrowedBooksButton.setOnAction(e -> viewAllBorrowedBooks());
        logoutButton.setOnAction(e -> showLoginPage(stage));

        Scene scene = new Scene(pane, 300, 400);
        stage.setScene(scene);
        stage.setTitle("Library Management System - Admin Panel");
        stage.show();
    }

    private void showStudentPanel(Stage stage) {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Button borrowButton = new Button("Borrow Book");
        Button returnButton = new Button("Return Book");
        Button viewFinesButton = new Button("View Fines");
        Button viewBorrowedBooksButton = new Button("View Borrowed Books");
        Button viewAvailableBooksButton = new Button("View Available Books");
        Button logoutButton = new Button("Logout");

        pane.getChildren().addAll(borrowButton, returnButton, viewFinesButton, viewBorrowedBooksButton, viewAvailableBooksButton, logoutButton);

        borrowButton.setOnAction(e -> borrowBook());
        returnButton.setOnAction(e -> returnBook());
        viewFinesButton.setOnAction(e -> viewFines());
        viewBorrowedBooksButton.setOnAction(e -> viewBorrowedBooks());
        viewAvailableBooksButton.setOnAction(e -> viewAvailableBooks());
        logoutButton.setOnAction(e -> showLoginPage(stage));

        Scene scene = new Scene(pane, 300, 400);
        stage.setScene(scene);
        stage.setTitle("Library Management System - Student Panel");

        if (currentStudent.getFine() > 0) {
            showAlert("Fine Notification", "You have an outstanding fine of $" + currentStudent.getFine());
        }

        stage.show();
    }

    private void showAccountsPanel(Stage stage) {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Button viewFinesButton = new Button("View All Fines");
        Button viewBorrowedBooksButton = new Button("View All Borrowed Books");
        Button logoutButton = new Button("Logout");

        pane.getChildren().addAll(viewFinesButton, viewBorrowedBooksButton, logoutButton);

        viewFinesButton.setOnAction(e -> viewAllFines());
        viewBorrowedBooksButton.setOnAction(e -> viewAllBorrowedBooks());
        logoutButton.setOnAction(e -> showLoginPage(stage));

        Scene scene = new Scene(pane, 300, 400);
        stage.setScene(scene);
        stage.setTitle("Library Management System - Accounts Panel");
        stage.show();
    }

    private void addBook() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Book");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField idField = new TextField();
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();
        Button addButton = new Button("Add");

        pane.add(new Label("ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(new Label("Title:"), 0, 1);
        pane.add(titleField, 1, 1);
        pane.add(new Label("Author:"), 0, 2);
        pane.add(authorField, 1, 2);
        pane.add(new Label("ISBN:"), 0, 3);
        pane.add(isbnField, 1, 3);
        pane.add(addButton, 1, 4);

        addButton.setOnAction(e -> {
            String id = idField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();

            library.addItem(new Book(id, title, author, isbn));
            dialog.close();
        });

        dialog.setScene(new Scene(pane, 300, 200));
        dialog.show();
    }

    private void removeBook() {
        Stage dialog = new Stage();
        dialog.setTitle("Remove Book");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField idField = new TextField();
        Button removeButton = new Button("Remove");

        pane.add(new Label("ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(removeButton, 1, 1);

        removeButton.setOnAction(e -> {
            String id = idField.getText();
            library.removeItem(id);
            dialog.close();
        });

        dialog.setScene(new Scene(pane, 300, 150));
        dialog.show();
    }

    private void editBook() {
        Stage dialog = new Stage();
        dialog.setTitle("Edit Book");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField idField = new TextField();
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();
        Button editButton = new Button("Edit");

        pane.add(new Label("ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(new Label("Title:"), 0, 1);
        pane.add(titleField, 1, 1);
        pane.add(new Label("Author:"), 0, 2);
        pane.add(authorField, 1, 2);
        pane.add(new Label("ISBN:"), 0, 3);
        pane.add(isbnField, 1, 3);
        pane.add(editButton, 1, 4);

        editButton.setOnAction(e -> {
            String id = idField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();

            library.editItem(id, title, author, isbn);
            dialog.close();
        });

        dialog.setScene(new Scene(pane, 300, 200));
        dialog.show();
    }

    private void viewBooks() {
        Stage dialog = new Stage();
        dialog.setTitle("View Books");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        library.getItems().forEach(item -> {
            if (item instanceof Book) {
                Book book = (Book) item;
                Label label = new Label("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
                pane.getChildren().add(label);
            }
        });

        dialog.setScene(new Scene(pane, 400, 400));
        dialog.show();
    }

    private void addStudent() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Student");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField nameField = new TextField();
        TextField idField = new TextField();
        TextField passwordField = new TextField();
        Button addButton = new Button("Add");

        pane.add(new Label("Name:"), 0, 0);
        pane.add(nameField, 1, 0);
        pane.add(new Label("ID:"), 0, 1);
        pane.add(idField, 1, 1);
        pane.add(new Label("Password:"), 0, 2);
        pane.add(passwordField, 1, 2);
        pane.add(addButton, 1, 3);

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String password = passwordField.getText();

            library.addStudent(new Student(name, id, password));
            dialog.close();
        });

        dialog.setScene(new Scene(pane, 300, 200));
        dialog.show();
    }

    private void addFine() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Fine");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField studentIdField = new TextField();
        TextField fineAmountField = new TextField();
        Button addButton = new Button("Add");

        pane.add(new Label("Student ID:"), 0, 0);
        pane.add(studentIdField, 1, 0);
        pane.add(new Label("Fine Amount:"), 0, 1);
        pane.add(fineAmountField, 1, 1);
        pane.add(addButton, 1, 2);

        addButton.setOnAction(e -> {
            String studentId = studentIdField.getText();
            double fineAmount = Double.parseDouble(fineAmountField.getText());

            Student student = library.getStudents().stream()
                    .filter(s -> s.getId().equals(studentId))
                    .findFirst()
                    .orElse(null);

            if (student != null) {
                student.addFine(fineAmount);
                dialog.close();
            } else {
                showAlert("Error", "Student not found.");
            }
        });

        dialog.setScene(new Scene(pane, 300, 200));
        dialog.show();
    }

    private void removeFine() {
        Stage dialog = new Stage();
        dialog.setTitle("Remove Fine");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField studentIdField = new TextField();
        TextField fineAmountField = new TextField();
        Button removeButton = new Button("Remove");

        pane.add(new Label("Student ID:"), 0, 0);
        pane.add(studentIdField, 1, 0);
        pane.add(new Label("Fine Amount:"), 0, 1);
        pane.add(fineAmountField, 1, 1);
        pane.add(removeButton, 1, 2);

        removeButton.setOnAction(e -> {
            String studentId = studentIdField.getText();
            double fineAmount = Double.parseDouble(fineAmountField.getText());

            Student student = library.getStudents().stream()
                    .filter(s -> s.getId().equals(studentId))
                    .findFirst()
                    .orElse(null);

            if (student != null) {
                student.removeFine(fineAmount);
                dialog.close();
            } else {
                showAlert("Error", "Student not found.");
            }
        });

        dialog.setScene(new Scene(pane, 300, 200));
        dialog.show();
    }

    private void viewAllFines() {
        Stage dialog = new Stage();
        dialog.setTitle("View All Fines");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        library.getStudents().forEach(student -> {
            if (student.getFine() > 0) {
                Label label = new Label("Student ID: " + student.getId() + ", Fine: $" + student.getFine());
                pane.getChildren().add(label);
            }
        });

        dialog.setScene(new Scene(pane, 400, 400));
        dialog.show();
    }

    private void viewAllBorrowedBooks() {
        Stage dialog = new Stage();
        dialog.setTitle("View All Borrowed Books");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        library.getStudents().forEach(student -> {
            student.getBorrowedBooks().forEach(book -> {
                Label label = new Label("Student ID: " + student.getId() + ", Book ID: " + book.getId() + ", Title: " + book.getTitle());
                pane.getChildren().add(label);
            });
        });

        dialog.setScene(new Scene(pane, 400, 400));
        dialog.show();
    }

    private void borrowBook() {
        Stage dialog = new Stage();
        dialog.setTitle("Borrow Book");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField bookIdField = new TextField();
        Button borrowButton = new Button("Borrow");

        pane.add(new Label("Book ID:"), 0, 0);
        pane.add(bookIdField, 1, 0);
        pane.add(borrowButton, 1, 1);

        borrowButton.setOnAction(e -> {
            String bookId = bookIdField.getText();
            Book book = (Book) library.searchItemById(bookId);

            if (book != null && book.isAvailable()) {
                currentStudent.borrowBook(book);
                showAlert("Success", "Book borrowed successfully.");
                dialog.close();
            } else if (book != null && !book.isAvailable()) {
                showAlert("Error", "This book is already borrowed by another student.");
            } else {
                showAlert("Error", "Book not found.");
            }
        });

        dialog.setScene(new Scene(pane, 300, 150));
        dialog.show();
    }

    private void returnBook() {
        Stage dialog = new Stage();
        dialog.setTitle("Return Book");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField bookIdField = new TextField();
        Button returnButton = new Button("Return");

        pane.add(new Label("Book ID:"), 0, 0);
        pane.add(bookIdField, 1, 0);
        pane.add(returnButton, 1, 1);

        returnButton.setOnAction(e -> {
            String bookId = bookIdField.getText();
            Book book = (Book) library.searchItemById(bookId);

            if (book != null && !book.isAvailable() && currentStudent.getBorrowedBooks().contains(book)) {
                currentStudent.returnBook(book);
                showAlert("Success", "Book returned successfully.");
                dialog.close();
            } else if (book != null && book.isAvailable()) {
                showAlert("Error", "This book is already available in the library.");
            } else {
                showAlert("Error", "Book not found.");
            }
        });

        dialog.setScene(new Scene(pane, 300, 150));
        dialog.show();
    }

    private void viewFines() {
        Stage dialog = new Stage();
        dialog.setTitle("View Fines");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Outstanding Fine: $" + currentStudent.getFine());
        pane.getChildren().add(label);

        dialog.setScene(new Scene(pane, 300, 100));
        dialog.show();
    }

    private void viewBorrowedBooks() {
        Stage dialog = new Stage();
        dialog.setTitle("View Borrowed Books");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        currentStudent.getBorrowedBooks().forEach(book -> {
            Label label = new Label("Book ID: " + book.getId() + ", Title: " + book.getTitle());
            pane.getChildren().add(label);
        });

        dialog.setScene(new Scene(pane, 400, 400));
        dialog.show();
    }

    private void viewAvailableBooks() {
        Stage dialog = new Stage();
        dialog.setTitle("View Available Books");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        library.getItems().stream()
                .filter(item -> item instanceof Book && ((Book) item).isAvailable())
                .forEach(item -> {
                    Book book = (Book) item;
                    Label label = new Label("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
                    pane.getChildren().add(label);
                });

        dialog.setScene(new Scene(pane, 400, 400));
        dialog.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
