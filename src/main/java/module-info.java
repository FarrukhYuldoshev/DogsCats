module com.example.dogcat {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.example.dogcat to javafx.fxml;
    exports com.example.dogcat;
}