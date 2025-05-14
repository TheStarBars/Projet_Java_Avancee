package com.example.projetjavaresto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminConnexionController {

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button connectButton                                ;

    public void connexion(javafx.event.ActionEvent event) throws IOException {
        Stage stage = null;
        Parent myNewScene = null;
       String pass = PasswordField.getText();
       if (pass.equals("admin")) {
           stage = (Stage) connectButton.getScene().getWindow();
           myNewScene = FXMLLoader.load(MainController.class.getResource("AdminPanelView.fxml"));
       }
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("KrampTeckResto");
        stage.show();
    }
}
