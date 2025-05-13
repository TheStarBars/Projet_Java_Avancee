module com.example.projetjavaresto {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.projetjavaresto to javafx.fxml;
    exports com.example.projetjavaresto;
}