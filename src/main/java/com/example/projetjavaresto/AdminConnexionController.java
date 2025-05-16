package com.example.projetjavaresto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the admin login view.
 * This controller handles password verification and navigation to the admin panel.
 */
public class AdminConnexionController {

    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button connectButton;

    /**
     * Handles the login logic. If the entered password matches the admin password,
     * the application navigates to the Admin Panel view.
     *
     * @param event the action event triggered by the button
     * @throws IOException if the FXML resource for the Admin Panel view cannot be loaded
     */
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

