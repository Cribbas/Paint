module com.example.paintt {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.swing;
    requires java.logging;
    requires org.testng;

    opens paintry to javafx.fxml;
    exports paintry;
}