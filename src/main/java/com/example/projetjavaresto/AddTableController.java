package com.example.projetjavaresto;

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
import static Utils.ReturnMainMenu.MainMenu;

/**
 * Controller class for the "Add Table" form.
 * Allows the user to input a table size, validate it,
 * and insert it into the database with a default status of "Libre".
 */
public class AddTableController {

    @FXML
    private TextField SizeField;

    /**
     * Handles the submission of the form to add a new table.
     * Validates the input and inserts the table into the `tables` database.
     *
     * @throws SQLException if a database error occurs during insertion.
     * @throws IOException  if an error occurs during view transition.
     */
    @FXML
    private void handleSubmit() throws SQLException, IOException {
        if (!SizeField.getText().matches("[0-9]+")) {
            showAlert(Alert.AlertType.ERROR, "Erreur ⚠", "La taille de la table doit être un nombre entier");
        } else if (SizeField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur ⚠", "Le champs doit être remplit.");
        } else {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `tables` (`taille`, `statut`) VALUES ('"
                            + SizeField.getText() + "', 'Libre')");

            showAlert(Alert.AlertType.INFORMATION, "Succès ✅", "Table ajouté avec succès.");
            SizeField.clear();
        }
    }

    /**
     * Returns to the main menu.
     *
     * @param event The JavaFX action event triggering the transition.
     * @throws IOException if the main menu view fails to load.
     */
    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }

    /**
     * Utility method to display an alert dialog.
     *
     * @param type    The type of alert.
     * @param title   The title of the dialog.
     * @param message The header message to display.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}

