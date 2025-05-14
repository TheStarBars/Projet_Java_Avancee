package com.example.projetjavaresto;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static Utils.ConnectDB.getConnection;
import static Utils.ReturnMainMenu.MainMenu;

public class AddTableController {
    @FXML
    private TextField NumberField;
    @FXML
    private TextField SizeField;

    @FXML
    private void handleSubmit() throws SQLException, IOException {
        if (!SizeField.getText().matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("votre prix de être du format 5.05");
            alert.showAndWait();
        }

        else if (SizeField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("les champs doivent etre complet");
            alert.showAndWait();
        } else {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `tables` (`taille`, `statut`) VALUES ('"
                            + SizeField.getText() +
                            "', 'Libre')");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Ajouter avec succes");
            alert.showAndWait();
            SizeField.clear();
        }
    }
        @FXML
        private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            MainMenu(stage);
        }
}
