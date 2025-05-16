package com.example.projetjavaresto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static Utils.Timer.*;

/**
 * Entry point of the KrampTeckResto application.
 * This class launches the JavaFX application and initializes the service timer.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application by loading the main view and displaying it.
     * Also initializes the countdown timer with a predefined duration.
     *
     * @param stage the primary stage for this application
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        scene.getStylesheets().add(getClass().getResource("/CSS/app.css").toExternalForm());
        stage.setTitle("KrampTeckResto");
        Image image = new Image(Main.class.getResourceAsStream("/assets/Logo.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}

