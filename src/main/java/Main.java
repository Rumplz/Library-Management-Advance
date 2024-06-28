import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {
    private Library library;
    private Student currentStudent;

    @Override
    public void start(Stage primaryStage) {
        library = new Library("My Library");

        // Prepopulate library with books
        populateLibraryWithBooks();

        // Prepopulate library with students
        populateLibraryWithStudents();


        showInitialPage(primaryStage);
    }

    private void populateLibraryWithBooks() {
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
    }

    private void populateLibraryWithStudents() {
        library.addStudent(new Student("John Doe", "student1", "password1"));
        library.addStudent(new Student("Jane Smith", "student2", "password2"));
    }

    private void showInitialPage(Stage primaryStage) {
        primaryStage.setTitle("Library System");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Button studentButton = new Button("Student Login");
        Button adminButton = new Button("Admin Login");
        pane.getChildren().addAll(studentButton, adminButton);

        studentButton.setOnAction(e -> showLoginPage(primaryStage));
        adminButton.setOnAction(e -> showAdminLoginPage(primaryStage));

        primaryStage.setScene(new Scene(pane, 300, 200));
        primaryStage.show();
    }

    private void showLoginPage(Stage primaryStage) {
        primaryStage.setTitle("Library System - Student Login");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button signupButton = new Button("New student? SignUp Here!");
        Button backButton = new Button("Back");

        pane.getChildren().addAll(new Label("Username:"), usernameField, new Label("Password:"), passwordField, loginButton, signupButton, backButton);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            currentStudent = library.getStudents().stream()
                    .filter(s -> s.getId().equals(username) && s.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);

            if (currentStudent != null) {
                showStudentPanel(primaryStage);
                if (currentStudent.getFine() > 0) {
                    showAlert("Notification", "You have outstanding fines: $" + currentStudent.getFine());
                }
            } else {
                showAlert("Error", "Invalid username or password.");
            }
        });

        signupButton.setOnAction(e -> showSignupPage(primaryStage));
        backButton.setOnAction(e -> showInitialPage(primaryStage));

        primaryStage.setScene(new Scene(pane, 300, 200));
        primaryStage.show();
    }

    private void showAdminLoginPage(Stage primaryStage) {
        primaryStage.setTitle("Library System - Admin Login");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        pane.getChildren().addAll(new Label("Username:"), usernameField, new Label("Password:"), passwordField, loginButton, backButton);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("admin") && password.equals("admin")) {
                showAdminPanel(primaryStage);
            } else if (username.equals("accounts") && password.equals("accounts")) {
                showAccountsPanel(primaryStage);
            } else {
                showAlert("Error", "Invalid admin credentials.");
            }
        });

        backButton.setOnAction(e -> showInitialPage(primaryStage));

        primaryStage.setScene(new Scene(pane, 300, 200));
        primaryStage.show();
    }

    private void showSignupPage(Stage primaryStage) {
        primaryStage.setTitle("Sign Up");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);

        TextField nameField = new TextField();
        TextField idField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button signupButton = new Button("Sign Up");
        Button backButton = new Button("Back");

        pane.add(new Label("Name:"), 0, 0);
        pane.add(nameField, 1, 0);
        pane.add(new Label("ID:"), 0, 1);
        pane.add(idField, 1, 1);
        pane.add(new Label("Password:"), 0, 2);
        pane.add(passwordField, 1, 2);
        pane.add(signupButton, 1, 3);
        pane.add(backButton, 1, 4);

        signupButton.setOnAction(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String password = passwordField.getText();

            if (!name.isEmpty() && !id.isEmpty() && !password.isEmpty()) {
                library.addStudent(new Student(name, id, password));
                showAlert("Success", "Account created successfully.");
                showLoginPage(primaryStage);
            } else {
                showAlert("Error", "All fields are required.");
            }
        });

        backButton.setOnAction(e -> showLoginPage(primaryStage));

        primaryStage.setScene(new Scene(pane, 300, 200));
        primaryStage.show();
    }

    private void showAdminPanel(Stage primaryStage) {
        primaryStage.setTitle("Admin Panel");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Button addBookButton = new Button("Add Book");
        Button removeBookButton = new Button("Remove Book");
        Button editBookButton = new Button("Edit Book");
        Button viewBooksButton = new Button("View Books");
        Button addStudentButton = new Button("Add Student");
        Button addFineButton = new Button("Add Fine");
        Button removeFineButton = new Button("Remove Fine");
        Button viewAllFinesButton = new Button("View All Fines");
        Button viewAllBorrowedBooksButton = new Button("View All Borrowed Books");
        Button logoutButton = new Button("Logout");

        pane.getChildren().addAll(addBookButton, removeBookButton, editBookButton, viewBooksButton, addStudentButton, addFineButton, removeFineButton, viewAllFinesButton, viewAllBorrowedBooksButton, logoutButton);

        addBookButton.setOnAction(e -> addBook());
        removeBookButton.setOnAction(e -> removeBook());
        editBookButton.setOnAction(e -> editBook());
        viewBooksButton.setOnAction(e -> viewBooks());
        addStudentButton.setOnAction(e -> addStudent());
        addFineButton.setOnAction(e -> addFine());
        removeFineButton.setOnAction(e -> removeFine());
        viewAllFinesButton.setOnAction(e -> viewAllFines());
        viewAllBorrowedBooksButton.setOnAction(e -> viewAllBorrowedBooks());
        logoutButton.setOnAction(e -> showLoginPage(primaryStage));

        primaryStage.setScene(new Scene(pane, 300, 400));
        primaryStage.show();
    }

    private void showAccountsPanel(Stage primaryStage) {
        primaryStage.setTitle("Accounts Panel");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Button viewAllFinesButton = new Button("View All Fines");
        Button viewAllBorrowedBooksButton = new Button("View All Borrowed Books");
        Button logoutButton = new Button("Logout");

        pane.getChildren().addAll(viewAllFinesButton, viewAllBorrowedBooksButton, logoutButton);

        viewAllFinesButton.setOnAction(e -> viewAllFines());
        viewAllBorrowedBooksButton.setOnAction(e -> viewAllBorrowedBooks());
        logoutButton.setOnAction(e -> showLoginPage(primaryStage));

        primaryStage.setScene(new Scene(pane, 300, 200));
        primaryStage.show();
    }

    private void showStudentPanel(Stage primaryStage) {
        primaryStage.setTitle("Student Panel");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Button borrowBookButton = new Button("Borrow Book");
        Button returnBookButton = new Button("Return Book");
        Button viewFinesButton = new Button("View Fines");
        Button viewBorrowedBooksButton = new Button("View Borrowed Books");
        Button viewAvailableBooksButton = new Button("View Available Books");
        Button logoutButton = new Button("Logout");

        pane.getChildren().addAll(borrowBookButton, returnBookButton, viewFinesButton, viewBorrowedBooksButton, viewAvailableBooksButton, logoutButton);

        borrowBookButton.setOnAction(e -> borrowBook());
        returnBookButton.setOnAction(e -> returnBook());
        viewFinesButton.setOnAction(e -> viewFines());
        viewBorrowedBooksButton.setOnAction(e -> viewBorrowedBooks());
        viewAvailableBooksButton.setOnAction(e -> viewAvailableBooks());
        logoutButton.setOnAction(e -> showLoginPage(primaryStage));

        primaryStage.setScene(new Scene(pane, 300, 400));
        primaryStage.show();
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

            if (!id.isEmpty() && !title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
                library.addItem(new Book(id, title, author, isbn));
                showAlert("Success", "Book added successfully.");
                dialog.close();
            } else {
                showAlert("Error", "All fields are required.");
            }
        });

        dialog.setScene(new Scene(pane, 300, 200));
        dialog.show();
    }

    private void removeBook() {
        Stage dialog = new Stage();
        dialog.setTitle("Remove Book");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        TextField idField = new TextField();
        Button removeButton = new Button("Remove");

        pane.getChildren().addAll(new Label("Book ID:"), idField, removeButton);

        removeButton.setOnAction(e -> {
            String id = idField.getText();
            library.removeItem(id);
            showAlert("Success", "Book removed successfully.");
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

            if (!id.isEmpty() && !title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
                library.editItem(id, title, author, isbn);
                showAlert("Success", "Book edited successfully.");
                dialog.close();
            } else {
                showAlert("Error", "All fields are required.");
            }
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
            Book book = (Book) item;
            Label label = new Label("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
            pane.getChildren().add(label);
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
        PasswordField passwordField = new PasswordField();
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

            if (!name.isEmpty() && !id.isEmpty() && !password.isEmpty()) {
                library.addStudent(new Student(name, id, password));
                showAlert("Success", "Student added successfully.");
                dialog.close();
            } else {
                showAlert("Error", "All fields are required.");
            }
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
        TextField amountField = new TextField();
        Button addButton = new Button("Add");

        pane.add(new Label("Student ID:"), 0, 0);
        pane.add(studentIdField, 1, 0);
        pane.add(new Label("Amount:"), 0, 1);
        pane.add(amountField, 1, 1);
        pane.add(addButton, 1, 2);

        addButton.setOnAction(e -> {
            String studentId = studentIdField.getText();
            String amountText = amountField.getText();

            try {
                double amount = Double.parseDouble(amountText);
                Student student = library.getStudents().stream()
                        .filter(s -> s.getId().equals(studentId))
                        .findFirst()
                        .orElse(null);

                if (student != null) {
                    student.addFine(amount);
                    showAlert("Success", "Fine added successfully.");
                    dialog.close();
                } else {
                    showAlert("Error", "Student not found.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid amount.");
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
        TextField amountField = new TextField();
        Button removeButton = new Button("Remove");

        pane.add(new Label("Student ID:"), 0, 0);
        pane.add(studentIdField, 1, 0);
        pane.add(new Label("Amount:"), 0, 1);
        pane.add(amountField, 1, 1);
        pane.add(removeButton, 1, 2);

        removeButton.setOnAction(e -> {
            String studentId = studentIdField.getText();
            String amountText = amountField.getText();

            try {
                double amount = Double.parseDouble(amountText);
                Student student = library.getStudents().stream()
                        .filter(s -> s.getId().equals(studentId))
                        .findFirst()
                        .orElse(null);

                if (student != null) {
                    student.removeFine(amount);
                    showAlert("Success", "Fine removed successfully.");
                    dialog.close();
                } else {
                    showAlert("Error", "Student not found.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid amount.");
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
            Label label = new Label("Student ID: " + student.getId() + ", Name: " + student.getName() + ", Fine: $" + student.getFine());
            pane.getChildren().add(label);
        });

        dialog.setScene(new Scene(pane, 400, 400));
        dialog.show();
    }

    private void viewAllBorrowedBooks() {
        Stage dialog = new Stage();
        dialog.setTitle("View All Borrowed Books");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        library.getItems().forEach(item -> {
            Book book = (Book) item;
            if (book.getBorrowedDate() != null) {
                Label label = new Label("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Borrowed Date: " + book.getBorrowedDate() + ", Borrowed By: " + book.getBorrowedBy());
                pane.getChildren().add(label);
            }
        });

        dialog.setScene(new Scene(pane, 400, 400));
        dialog.show();
    }

    private void borrowBook() {
        Stage dialog = new Stage();
        dialog.setTitle("Borrow Book");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        TextField idField = new TextField();
        Button borrowButton = new Button("Borrow");

        pane.getChildren().addAll(new Label("Book ID:"), idField, borrowButton);

        borrowButton.setOnAction(e -> {
            String id = idField.getText();
            Book book = (Book) library.getItemById(id);

            if (book != null && book.getBorrowedDate() == null) {
                if (currentStudent.getBorrowedBooks().contains(book)) {
                    showAlert("Error", "You already have this book.");
                } else {
                    currentStudent.borrowBook(book);
                    book.borrow(currentStudent.getId());
                    showAlert("Success", "Book borrowed successfully.");
                }
            } else {
                showAlert("Error", "Book not available.");
            }
            dialog.close();
        });

        dialog.setScene(new Scene(pane, 300, 150));
        dialog.show();
    }

    private void returnBook() {
        Stage dialog = new Stage();
        dialog.setTitle("Return Book");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        TextField idField = new TextField();
        Button returnButton = new Button("Return");

        pane.getChildren().addAll(new Label("Book ID:"), idField, returnButton);

        returnButton.setOnAction(e -> {
            String id = idField.getText();
            Book book = (Book) library.getItemById(id);

            if (book != null && book.getBorrowedDate() != null) {
                currentStudent.returnBook(book);
                book.returnBook();
                showAlert("Success", "Book returned successfully.");
            } else {
                showAlert("Error", "Book not borrowed.");
            }
            dialog.close();
        });

        dialog.setScene(new Scene(pane, 300, 150));
        dialog.show();
    }

    private void viewFines() {
        Stage dialog = new Stage();
        dialog.setTitle("View Fines");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Outstanding Fines: $" + currentStudent.getFine());
        pane.getChildren().add(label);

        dialog.setScene(new Scene(pane, 300, 150));
        dialog.show();
    }

    private void viewBorrowedBooks() {
        Stage dialog = new Stage();
        dialog.setTitle("View Borrowed Books");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));

        currentStudent.getBorrowedBooks().forEach(book -> {
            Label label = new Label("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Borrowed Date: " + book.getBorrowedDate());
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

        library.getItems().forEach(item -> {
            Book book = (Book) item;
            if (book.getBorrowedDate() == null) {
                Label label = new Label("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
                pane.getChildren().add(label);
            }
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