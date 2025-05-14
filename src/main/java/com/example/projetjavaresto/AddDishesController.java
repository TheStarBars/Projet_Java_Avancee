package com.example.projetjavaresto;


import Utils.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static Utils.ReturnMainMenu.MainMenu;

public class AddDishesController extends ConnectDB {
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private Label pathLabel;


    @FXML
    private void UploadFile() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Sélectionner un fichier");
        // Ouvre la boîte de dialogue et récupère le fichier choisi
        File selectedFile = chooser.showOpenDialog(null);
        if (selectedFile != null) {
        String path = selectedFile.getAbsolutePath().replace("\\", "\\\\");
        pathLabel.setText(path);
        System.out.println("Fichier sélectionné : " + path);
    } else {
        System.out.println("Aucun fichier sélectionné.");
    }


}



    @FXML
    private void handleSubmit() throws SQLException, IOException {
        String name = nameField.getText();
        if (name.length() >= 100) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("trop long");
            alert.showAndWait();
        }
        else if (!priceField.getText().matches("\\b\\d+.\\d{2}\\b") ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("votre prix de être du format 5.05");
            alert.showAndWait();
        }
        String desc = descriptionField.getText();
        Double price = Double.valueOf(priceField.getText());

        if(name.isEmpty() || desc.isEmpty() || price <= 0 || pathLabel.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("les champs doivent etre complet");
            alert.showAndWait();
        }
        else {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `plats` (`nom`, `description`, `prix`,`image`) VALUES ('"
                            + name + "', '"
                            + desc + "', '"
                            + price + "','"
                            + pathLabel.getText() + "')");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Ajouter avec succes");
            alert.showAndWait();
            nameField.clear();
            descriptionField.clear();
            priceField.clear();
            pathLabel.setText("");
        }
    }

    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }

}