module com.example.paint4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;




    opens com.example.PaintTry to javafx.fxml;
    exports com.example.PaintTry;
}