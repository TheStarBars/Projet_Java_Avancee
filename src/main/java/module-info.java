module com.example.projetjavaresto {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires transitive com.google.gson;
    requires kernel;
    requires layout;

    opens com.example.projetjavaresto to javafx.fxml;
    opens Class to com.google.gson;
    exports com.example.projetjavaresto;
    opens Utils to com.google.gson;
}