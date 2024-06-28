import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    private Library library;
    private Student currentStudent;

    @Override
    public void start(Stage primaryStage) {
        library = new Library("Iqra University Library Managment System ");


        populateLibraryWithBooks();


        populateLibraryWithStudents();


        VBox root = new VBox();
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());



        // Set the scene to the primary stage
        primaryStage.setScene(scene);

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
        library.addStudent(new Student("Muhammad Mujtaba", "student1", "password1"));
        library.addStudent(new Student("Muhammad Mustafa", "student2", "password2"));
    }

    private void showInitialPage(Stage primaryStage) {
        primaryStage.setTitle("Library System");

        VBox pane = new VBox(100);
        pane.setPadding(new Insets(100, 100, 100, 100));
        pane.getStyleClass().add("pane"); // Add CSS class to VBox

        Label titleLabel = new Label("Chose Student Login OR Admin Login");
        titleLabel.setFont(Font.font("Arial", 24)); // Set font size to 24
        titleLabel.setStyle("-fx-font-weight: bold"); // Make it bold
        titleLabel.getStyleClass().add("titleLabel"); // Add CSS class to title label
        pane.getChildren().add(titleLabel);

        Button studentButton = new Button("Student Login");
        studentButton.getStyleClass().add("button"); // Add CSS class to Button

        Button adminButton = new Button("Admin Login");
        adminButton.getStyleClass().add("button"); // Add CSS class to Button

        pane.getChildren().addAll(studentButton, adminButton);

        studentButton.setOnAction(e -> showLoginPage(primaryStage));
        adminButton.setOnAction(e -> showAdminLoginPage(primaryStage));

        Scene initialScene = new Scene(pane, 1000, 1000); // Rename the scene variable
        initialScene.getStylesheets().add("style.css"); // Add CSS stylesheet
        primaryStage.setScene(initialScene);
        primaryStage.show();
    }
    private void showLoginPage(Stage primaryStage) {
        primaryStage.setTitle("Student Login");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getStyleClass().add("pane"); // Add CSS class to VBox

        // Add a big title label
        Label titleLabel = new Label("Student Login");
        titleLabel.setFont(Font.font("Arial", 24)); // Set font size to 24
        titleLabel.setStyle("-fx-font-weight: bold"); // Make it bold
        titleLabel.getStyleClass().add("titleLabel"); // Add CSS class to title label
        pane.getChildren().add(titleLabel);

        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("textField"); // Add CSS class to TextField

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("textField"); // Add CSS class to PasswordField

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button"); // Add CSS class to Button

        Button signupButton = new Button("SignUp Here!");
        signupButton.getStyleClass().add("button"); // Add CSS class to Button

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button"); // Add CSS class to Button

        pane.getChildren().addAll(new Label("Username:"), usernameField, new Label("Password:"), passwordField, loginButton, signupButton, backButton);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            currentStudent = library.getStudents().stream()
                    .filter(s -> s.getId().equals(username) && s.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);

            if (currentStudent!= null) {
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

        Scene loginScene = new Scene(pane, 1000, 1000); // Rename the scene variable
        loginScene.getStylesheets().add("stylee.css"); // Add CSS stylesheet
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showAdminLoginPage(Stage primaryStage) {
        primaryStage.setTitle("Library System - Admin Login");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getStyleClass().add("pane"); // Add CSS class to VBox

        // Add a big title label
        Label titleLabel = new Label("Admin Login");
        titleLabel.setFont(Font.font("Arial", 24)); // Set font size to 24
        titleLabel.setStyle("-fx-font-weight: bold"); // Make it bold
        titleLabel.getStyleClass().add("titleLabel"); // Add CSS class to title label
        pane.getChildren().add(titleLabel);

        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("textField"); // Add CSS class to TextField

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("textField"); // Add CSS class to PasswordField

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button"); // Add CSS class to Button

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button"); // Add CSS class to Button

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

        Scene adminLoginScene = new Scene(pane, 1000, 1000); // Rename the scene variable
        adminLoginScene.getStylesheets().add("style.css"); // Add CSS stylesheet
        primaryStage.setScene(adminLoginScene);
        primaryStage.show();
    }
    private void showSignupPage(Stage primaryStage) {
        primaryStage.setTitle("Sign Up");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);
        pane.getStyleClass().add("pane"); // Add CSS class to GridPane

        // Add a big title label
        Label titleLabel = new Label("Sign Up");
        titleLabel.setFont(Font.font("Arial", 24)); // Set font size to 24
        titleLabel.setStyle("-fx-font-weight: bold"); // Make it bold
        titleLabel.getStyleClass().add("titleLabel"); // Add CSS class to title label
        pane.add(titleLabel, 0, 0, 2, 1); // Span across two columns

        TextField nameField = new TextField();
        nameField.getStyleClass().add("textField"); // Add CSS class to TextField

        TextField idField = new TextField();
        idField.getStyleClass().add("textField"); // Add CSS class to TextField

        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("textField"); // Add CSS class to PasswordField

        Button signupButton = new Button("Sign Up");
        signupButton.getStyleClass().add("button"); // Add CSS class to Button

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button"); // Add CSS class to Button

        pane.add(new Label("Name:"), 0, 1);
        pane.add(nameField, 1, 1);
        pane.add(new Label("ID:"), 0, 2);
        pane.add(idField, 1, 2);
        pane.add(new Label("Password:"), 0, 3);
        pane.add(passwordField, 1, 3);
        pane.add(signupButton, 1, 4);
        pane.add(backButton, 1, 5);

        signupButton.setOnAction(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String password = passwordField.getText();

            if (!name.isEmpty() &&!id.isEmpty() &&!password.isEmpty()) {
                library.addStudent(new Student(name, id, password));
                showAlert("Success", "Account created successfully.");
                showLoginPage(primaryStage);
            } else {
                showAlert("Error", "All fields are required.");
            }
        });

        backButton.setOnAction(e -> showLoginPage(primaryStage));

        Scene signupScene = new Scene(pane, 1000, 1000); // Rename the scene variable
        signupScene.getStylesheets().add("style.css"); // Add CSS stylesheet
        primaryStage.setScene(signupScene);
        primaryStage.show();
    }

    private void showAdminPanel(Stage primaryStage) {
        primaryStage.setTitle("Admin Panel");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getStyleClass().add("pane"); // Add CSS class to VBox

        Button addBookButton = new Button("Add Book");
        addBookButton.getStyleClass().add("button"); // Add CSS class to Button

        Button removeBookButton = new Button("Remove Book");
        removeBookButton.getStyleClass().add("button"); // Add CSS class to Button

        Button editBookButton = new Button("Edit Book");
        editBookButton.getStyleClass().add("button"); // Add CSS class to Button

        Button viewBooksButton = new Button("View Books");
        viewBooksButton.getStyleClass().add("button"); // Add CSS class to Button

        Button addStudentButton = new Button("Add Student");
        addStudentButton.getStyleClass().add("button"); // Add CSS class to Button

        Button addFineButton = new Button("Add Fine");
        addFineButton.getStyleClass().add("button"); // Add CSS class to Button

        Button removeFineButton = new Button("Remove Fine");
        removeFineButton.getStyleClass().add("button"); // Add CSS class to Button

        Button viewAllFinesButton = new Button("View All Fines");
        viewAllFinesButton.getStyleClass().add("button"); // Add CSS class to Button

        Button viewAllBorrowedBooksButton = new Button("View All Borrowed Books");
        viewAllBorrowedBooksButton.getStyleClass().add("button"); // Add CSS class to Button

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("button"); // Add CSS class to Button

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
        logoutButton.setOnAction(e -> showAdminLoginPage(primaryStage));

        Scene adminPanelScene = new Scene(pane, 1000, 1000); // Rename the scene variable
        adminPanelScene.getStylesheets().add("style.css"); // Add CSS stylesheet
        primaryStage.setScene(adminPanelScene);
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

        primaryStage.setScene(new Scene(pane, 1000, 1000));
        primaryStage.show();
    }

    private void showStudentPanel(Stage primaryStage) {
        primaryStage.setTitle("Student Panel");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getStyleClass().add("pane"); // Add CSS class to VBox

        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.getStyleClass().add("button"); // Add CSS class to Button

        Button returnBookButton = new Button("Return Book");
        returnBookButton.getStyleClass().add("button"); // Add CSS class to Button

        Button viewFinesButton = new Button("View Fines");
        viewFinesButton.getStyleClass().add("button"); // Add CSS class to Button

        Button viewBorrowedBooksButton = new Button("View Borrowed Books");
        viewBorrowedBooksButton.getStyleClass().add("button"); // Add CSS class to Button

        Button viewAvailableBooksButton = new Button("View Available Books");
        viewAvailableBooksButton.getStyleClass().add("button"); // Add CSS class to Button

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("button"); // Add CSS class to Button

        pane.getChildren().addAll(borrowBookButton, returnBookButton, viewFinesButton, viewBorrowedBooksButton, viewAvailableBooksButton, logoutButton);

        borrowBookButton.setOnAction(e -> borrowBook());
        returnBookButton.setOnAction(e -> returnBook());
        viewFinesButton.setOnAction(e -> viewFines());
        viewBorrowedBooksButton.setOnAction(e -> viewBorrowedBooks());
        viewAvailableBooksButton.setOnAction(e -> viewAvailableBooks());
        logoutButton.setOnAction(e -> showLoginPage(primaryStage));

        Scene scene = new Scene(pane, 1000, 1000);
        scene.getStylesheets().add("style.css"); // Add CSS stylesheet
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBook() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Book");

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);
        pane.getStyleClass().add("pane"); // Add CSS class to GridPane

        TextField idField = new TextField();
        idField.getStyleClass().add("textField"); // Add CSS class to TextField

        TextField titleField = new TextField();
        titleField.getStyleClass().add("textField"); // Add CSS class to TextField

        TextField authorField = new TextField();
        authorField.getStyleClass().add("textField"); // Add CSS class to TextField

        TextField isbnField = new TextField();
        isbnField.getStyleClass().add("textField"); // Add CSS class to TextField

        Button addButton = new Button("Add");
        addButton.getStyleClass().add("button"); // Add CSS class to Button

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

        Scene addBookScene = new Scene(pane, 1000, 1000); // Rename the scene variable
        addBookScene.getStylesheets().add("style.css"); // Add CSS stylesheet
        dialog.setScene(addBookScene);
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

        Scene removeBookScene = new Scene(pane, 1000, 1000); // Rename the scene variable
        removeBookScene.getStylesheets().add("style.css"); // Add CSS stylesheet
        dialog.setScene(removeBookScene);
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
//
        dialog.setScene(new Scene(pane, 1000, 1000));
        dialog.show();
    }

    private void viewBooks() {
        Stage dialog = new Stage();
        dialog.setTitle("View Books");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getStyleClass().add("root"); // Add CSS class to VBox

        library.getItems().forEach(item -> {
            Book book = (Book) item;
            Label label = new Label("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
            label.getStyleClass().add("label"); // Add CSS class to Label
            pane.getChildren().add(label);
        });

        Scene scene = new Scene(pane, 1000, 1000);
        scene.getStylesheets().add("style.css"); // Add CSS stylesheet
        dialog.setScene(scene);
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

        dialog.setScene(new Scene(pane, 1000, 1000));
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

        dialog.setScene(new Scene(pane, 1000, 1000));
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

        dialog.setScene(new Scene(pane, 1000, 1000));
        dialog.show();
    }

    private void viewAllFines() {
        Stage dialog = new Stage();
        dialog.setTitle("View All Fines");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getStyleClass().add("root"); // Add CSS class to VBox

        library.getStudents().forEach(student -> {
            Label label = new Label("Student ID: " + student.getId() + ", Name: " + student.getName() + ", Fine: $" + student.getFine());
            label.getStyleClass().add("label"); // Add CSS class to Label
            pane.getChildren().add(label);
        });

        Scene scene = new Scene(pane, 1000, 1000);
        scene.getStylesheets().add("style.css"); // Add CSS stylesheet
        dialog.setScene(scene);
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
                label.getStyleClass().add("label");
                pane.getChildren().add(label);

            }
        });

        Scene scene = new Scene(pane, 1000, 1000);
        scene.getStylesheets().add("style.css"); // Add CSS stylesheet
        dialog.setScene(scene);
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

        dialog.setScene(new Scene(pane, 1000, 1000));
        dialog.show();
    }

    private void viewAvailableBooks() {
        Stage dialog = new Stage();
        dialog.setTitle("View Available Books");

        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getStyleClass().add("pane"); // Add CSS class to VBox

        library.getItems().forEach(item -> {
            Book book = (Book) item;
            if (book.getBorrowedDate() == null) {
                Label label = new Label("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
                label.getStyleClass().add("label"); // Add CSS class to Label
                label.setFont(Font.font("Arial", 14)); // Set font family and size
                label.setTextFill(javafx.scene.paint.Color.BLACK); // Set text color
                pane.getChildren().add(label);
            }
        });

        Scene scene = new Scene(pane, 1000, 1000);
        scene.getStylesheets().add("style.css"); // Add CSS stylesheet
        dialog.setScene(scene);
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
