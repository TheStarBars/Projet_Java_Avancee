package com.example.projetjavaresto;

import Utils.ConnectDB;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static Utils.ConnectDB.getConnection;
import static Utils.ReturnMainMenu.AdminMenu;

public class AdminPanelAddEmployeeController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField postField;
    @FXML
    private TextField ageField;

    @FXML
    private void handleSubmit() throws SQLException, IOException {
        String name = nameField.getText();
        String post = postField.getText();
        String age = ageField.getText();
        if (name.length() >= 100) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("trop long");
            alert.showAndWait();
        }
        else if (!age.matches("[0-9]+") ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("indiquer votre age");
            alert.showAndWait();
        } else if (post.length() >= 100) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("trop long");
            alert.showAndWait();
        }


        if(name.isEmpty() || age.isEmpty() || post.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("les champs doivent etre complet");
            alert.showAndWait();
        }
        else {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `employe` (`nom`, `poste`, `nb_heure_travaillee`,`age`) VALUES ('"
                            + name + "', '"
                            + post + "', '"
                            + 0 + "','"
                            + Integer.parseInt(age) + "')");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Ajouter avec succes");
            alert.showAndWait();
            nameField.clear();
            postField.clear();
            ageField.clear();
        }
    }

    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminMenu(stage);
    }

}
