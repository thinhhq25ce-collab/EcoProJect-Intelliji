module com.example.connectdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Cần thiết để kết nối Database!

    opens com.example.connectdb to javafx.fxml;
    exports com.example.connectdb.util;
    exports com.example.connectdb.model;
}