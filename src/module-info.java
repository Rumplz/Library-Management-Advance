module LibraryManagementSystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.library to javafx.fxml;
    exports com.example.library;
}
