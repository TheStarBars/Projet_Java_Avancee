package com.example.projetjavaresto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static Utils.Timer.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        scene.getStylesheets().add(getClass().getResource("/CSS/app.css").toExternalForm());
        stage.setTitle("KrampTeckResto");
        Image image = new Image(Main.class.getResourceAsStream("Logo.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
        createInstance(25);
    }

    public static void main(String[] args) {
        launch();
    }
}