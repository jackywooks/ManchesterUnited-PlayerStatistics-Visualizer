module org.example.dataVisualizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires java.net.http;
//    requires json;
    requires com.google.gson;

    opens org.example.dataVisualizer to javafx.fxml, com.google.gson;

    exports org.example.dataVisualizer;
}