import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Library library = new Library("My Library");
    private Student currentStudent = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showLoginPage(primaryStage);
    }

    private void showLoginPage(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        vbox.getChildren().addAll(new Label("ID:"), usernameField, new Label("Password:"), passwordField, loginButton);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.equals("admin") && password.equals("admin")) {
                showAdminPanel(stage);
            } else if (username.startsWith("student") && password.equals("password" + username.charAt(username.length() - 1))) {
                currentStudent = library.getStudents().stream()
                        .filter(student -> student.getId().equals(username))
                        .findFirst()
                        .orElse(null);
                if (currentStudent != null && currentStudent.getFine() > 0) {
                    showAlert("Please pay the fines");
                }
                showStudentPanel(stage);
            } else if (username.equals("accounts") && password.equals("accounts")) {
                showAccountsPanel(stage);
            } else {
                showAlert("Invalid login credentials");
            }
        });

        stage.setScene(new Scene(vbox, 300, 200));
        stage.setTitle("Library Management System - Login");
        stage.show();
    }

    private void showAdminPanel(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Button addBookButton = new Button("Add Book");
        Button addMagazineButton = new Button("Add Magazine");
        Button viewLibraryButton = new Button("View Library");
        Button addStudentButton = new Button("Add Student");
        Button addFineButton = new Button("Add Fine");
        Button removeFineButton = new Button("Remove Fine");
        Button editBookButton = new Button("Edit Book");
        Button deleteBookButton = new Button("Delete Book");
        Button logoutButton = new Button("Logout");

        vbox.getChildren().addAll(addBookButton, addMagazineButton, viewLibraryButton, addStudentButton, addFineButton, removeFineButton, editBookButton, deleteBookButton, logoutButton);

        addBookButton.setOnAction(e -> addBook());
        addMagazineButton.setOnAction(e -> addMagazine());
        viewLibraryButton.setOnAction(e -> viewLibrary());
        addStudentButton.setOnAction(e -> addStudent());
        addFineButton.setOnAction(e -> addFine());
        removeFineButton.setOnAction(e -> removeFine());
        editBookButton.setOnAction(e -> editBook());
        deleteBookButton.setOnAction(e -> deleteBook());
        logoutButton.setOnAction(e -> showLoginPage(stage));

        stage.setScene(new Scene(vbox, 400, 400));
        stage.setTitle("Admin Panel");
        stage.show();
    }

    private void showStudentPanel(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Button borrowBookButton = new Button("Borrow Book");
        Button returnBookButton = new Button("Return Book");
        Button viewFinesButton = new Button("View Fines");
        Button viewBorrowedBooksButton = new Button("View Borrowed Books");
        Button logoutButton = new Button("Logout");

        vbox.getChildren().addAll(borrowBookButton, returnBookButton, viewFinesButton, viewBorrowedBooksButton, logoutButton);

        borrowBookButton.setOnAction(e -> borrowBook());
        returnBookButton.setOnAction(e -> returnBook());
        viewFinesButton.setOnAction(e -> viewFines());
        viewBorrowedBooksButton.setOnAction(e -> viewBorrowedBooks());
        logoutButton.setOnAction(e -> showLoginPage(stage));

        stage.setScene(new Scene(vbox, 300, 200));
        stage.setTitle("Student Panel");
        stage.show();
    }

    private void showAccountsPanel(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Button viewAllFinesButton = new Button("View All Fines");
        Button viewAllBorrowedBooksButton = new Button("View All Borrowed Books");
        Button logoutButton = new Button("Logout");

        vbox.getChildren().addAll(viewAllFinesButton, viewAllBorrowedBooksButton, logoutButton);

        viewAllFinesButton.setOnAction(e -> viewAllFines());
        viewAllBorrowedBooksButton.setOnAction(e -> viewAllBorrowedBooks());
        logoutButton.setOnAction(e -> showLoginPage(stage));

        stage.setScene(new Scene(vbox, 300, 200));
        stage.setTitle("Accounts Panel");
        stage.show();
    }
    private void addBook() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));

        TextField idField = new TextField();
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();
        Button submitButton = new Button("Add Book");

        pane.add(new Label("ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(new Label("Title:"), 0, 1);
        pane.add(titleField, 1, 1);
        pane.add(new Label("Author:"), 0, 2);
        pane.add(authorField, 1, 2);
        pane.add(new Label("ISBN:"), 0, 3);
        pane.add(isbnField, 1, 3);
        pane.add(submitButton, 1, 4);

        submitButton.setOnAction(e -> {
            String id = idField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();

            Book book = new Book(id, title, author, isbn);
            library.addItem(book);

            stage.close();
        });

        stage.setScene(new Scene(pane, 300, 250));
        stage.setTitle("Add Book");
        stage.show();
    }

    private void addMagazine() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));

        TextField idField = new TextField();
        TextField titleField = new TextField();
        TextField publisherField = new TextField();
        Button submitButton = new Button("Add Magazine");

        pane.add(new Label("ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(new Label("Title:"), 0, 1);
        pane.add(titleField, 1, 1);
        pane.add(new Label("Publisher:"), 0, 2);
        pane.add(publisherField, 1, 2);
        pane.add(submitButton, 1, 3);

        submitButton.setOnAction(e -> {
            String id = idField.getText();
            String title = titleField.getText();
            String publisher = publisherField.getText();

            Magazine magazine = new Magazine(id, title, publisher);
            library.addItem(magazine);

            stage.close();
        });

        stage.setScene(new Scene(pane, 300, 250));
        stage.setTitle("Add Magazine");
        stage.show();
    }

    private void viewLibrary() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (LibraryItem item : library.getItems()) {
            sb.append(item.toString()).append("\n");
        }
        textArea.setText(sb.toString());

        vbox.getChildren().add(textArea);

        stage.setScene(new Scene(vbox, 400, 400));
        stage.setTitle("View Library");
        stage.show();
    }

    private void addStudent() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField passwordField = new TextField();
        Button submitButton = new Button("Add Student");

        pane.add(new Label("ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(new Label("Name:"), 0, 1);
        pane.add(nameField, 1, 1);
        pane.add(new Label("Password:"), 0, 2);
        pane.add(passwordField, 1, 2);
        pane.add(submitButton, 1, 3);

        submitButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String password = passwordField.getText();

            Student student = new Student(name, id, password);
            library.addStudent(student);

            stage.close();
        });

        stage.setScene(new Scene(pane, 300, 250));
        stage.setTitle("Add Student");
        stage.show();
    }

    private void addFine() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));

        TextField idField = new TextField();
        TextField fineField = new TextField();
        Button submitButton = new Button("Add Fine");

        pane.add(new Label("Student ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(new Label("Fine Amount:"), 0, 1);
        pane.add(fineField, 1, 1);
        pane.add(submitButton, 1, 2);

        submitButton.setOnAction(e -> {
            String studentId = idField.getText();
            double fine = Double.parseDouble(fineField.getText());

            library.addFine(studentId, fine);

            stage.close();
        });

        stage.setScene(new Scene(pane, 300, 200));
        stage.setTitle("Add Fine");
        stage.show();
    }

    private void removeFine() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));

        TextField idField = new TextField();
        TextField fineField = new TextField();
        Button submitButton = new Button("Remove Fine");

        pane.add(new Label("Student ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(new Label("Fine Amount:"), 0, 1);
        pane.add(fineField, 1, 1);
        pane.add(submitButton, 1, 2);

        submitButton.setOnAction(e -> {
            String studentId = idField.getText();
            double fine = Double.parseDouble(fineField.getText());

            library.removeFine(studentId, fine);

            stage.close();
        });

        stage.setScene(new Scene(pane, 300, 200));
        stage.setTitle("Remove Fine");
        stage.show();
    }

    private void editBook() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));

        TextField idField = new TextField();
        TextField titleField = new TextField();
        Button submitButton = new Button("Edit Book");

        pane.add(new Label("Book ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(new Label("New Title:"), 0, 1);
        pane.add(titleField, 1, 1);
        pane.add(submitButton, 1, 2);

        submitButton.setOnAction(e -> {
            String id = idField.getText();
            String newTitle = titleField.getText();

            library.editItem(id, newTitle);

            stage.close();
        });

        stage.setScene(new Scene(pane, 300, 200));
        stage.setTitle("Edit Book");
        stage.show();
    }

    private void deleteBook() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));

        TextField idField = new TextField();
        Button submitButton = new Button("Delete Book");

        pane.add(new Label("Book ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(submitButton, 1, 1);

        submitButton.setOnAction(e -> {
            String id = idField.getText();
            library.removeItem(id);
            stage.close();
        });

        stage.setScene(new Scene(pane, 300, 200));
        stage.setTitle("Delete Book");
        stage.show();
    }

    private void borrowBook() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));

        TextField idField = new TextField();
        Button submitButton = new Button("Borrow Book");

        pane.add(new Label("Book ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(submitButton, 1, 1);

        submitButton.setOnAction(e -> {
            String bookId = idField.getText();
            Book book = (Book) library.getItems().stream()
                    .filter(item -> item instanceof Book && item.getId().equals(bookId))
                    .findFirst()
                    .orElse(null);
            if (book != null) {
                currentStudent.borrowBook(book);
            } else {
                showAlert("Book not found");
            }
            stage.close();
        });

        stage.setScene(new Scene(pane, 300, 200));
        stage.setTitle("Borrow Book");
        stage.show();
    }

    private void returnBook() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20, 20, 20, 20));

        TextField idField = new TextField();
        Button submitButton = new Button("Return Book");

        pane.add(new Label("Book ID:"), 0, 0);
        pane.add(idField, 1, 0);
        pane.add(submitButton, 1, 1);

        submitButton.setOnAction(e -> {
            String bookId = idField.getText();
            Book book = currentStudent.getBorrowedBooks().stream()
                    .filter(b -> b.getId().equals(bookId))
                    .findFirst()
                    .orElse(null);
            if (book != null) {
                currentStudent.returnBook(book);
            } else {
                showAlert("Book not found");
            }
            stage.close();
        });

        stage.setScene(new Scene(pane, 300, 200));
        stage.setTitle("Return Book");
        stage.show();
    }

    private void viewFines() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setText("Total Fine: $" + currentStudent.getFine());

        vbox.getChildren().add(textArea);

        stage.setScene(new Scene(vbox, 300, 200));
        stage.setTitle("View Fines");
        stage.show();
    }

    private void viewBorrowedBooks() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (Book book : currentStudent.getBorrowedBooks()) {
            sb.append(book.toString()).append("\n");
        }
        textArea.setText(sb.toString());

        vbox.getChildren().add(textArea);

        stage.setScene(new Scene(vbox, 400, 400));
        stage.setTitle("View Borrowed Books");
        stage.show();
    }

    private void viewAllFines() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (Student student : library.getStudents()) {
            sb.append(student.getName()).append(": $").append(student.getFine()).append("\n");
        }
        textArea.setText(sb.toString());

        vbox.getChildren().add(textArea);

        stage.setScene(new Scene(vbox, 400, 400));
        stage.setTitle("View All Fines");
        stage.show();
    }

    private void viewAllBorrowedBooks() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (Student student : library.getStudents()) {
            sb.append(student.getName()).append(":\n");
            for (Book book : student.getBorrowedBooks()) {
                sb.append("  - ").append(book.toString()).append("\n");
            }
        }
        textArea.setText(sb.toString());

        vbox.getChildren().add(textArea);

        stage.setScene(new Scene(vbox, 400, 400));
        stage.setTitle("View All Borrowed Books");
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}