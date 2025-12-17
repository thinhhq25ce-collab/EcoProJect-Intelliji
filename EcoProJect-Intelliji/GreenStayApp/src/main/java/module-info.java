module udn.vku.greenstayapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;

    opens udn.vku.greenstayapp.model to javafx.base;
    opens udn.vku.greenstayapp to javafx.fxml;
    opens udn.vku.greenstayapp.presentation.controller to javafx.fxml;

    exports udn.vku.greenstayapp;
}