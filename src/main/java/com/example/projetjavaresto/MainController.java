package com.example.projetjavaresto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainController {
    @FXML
    public Label AddDishesLabel;
    @FXML
    public Label ListDishesLabel;
    @FXML
    public Button AddDishesButton;
    @FXML
    public Button ListDishesButton;
    public void NavigateTo(javafx.event.ActionEvent event ) throws IOException {
        Stage stage = null;
        Parent myNewScene = null;

        if (event.getSource() == AddDishesButton){
            stage = (Stage) AddDishesButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AddDishesView.fxml"));
        }else if (event.getSource() == ListDishesButton) {
            stage = (Stage) ListDishesButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("ListDishesView.fxml"));
        }

        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("KrampTeckResto");
        stage.show();

    }

}
