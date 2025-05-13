package com.example.projetjavaresto;


import Utils.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
        JFileChooser chooser = new JFileChooser();
        String path = "";
        chooser.showOpenDialog(null);
        if (chooser.getSelectedFile() != null) {
            File picture = chooser.getSelectedFile();
            path = picture.getPath().replace("\\", "\\\\");
            pathLabel.setText(path);

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
        if (!priceField.getText().matches("\\b\\d+.\\d{2}\\b") ) {
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
            int rs = statement.executeUpdate(
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
}