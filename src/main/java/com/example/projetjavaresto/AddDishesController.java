package com.example.projetjavaresto;

import Utils.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static Utils.ReturnMainMenu.MainMenu;

/**
 * Controller class for managing the form to add a new dish.
 * Allows uploading an image file, filling dish details,
 * and submitting the data to the database.
 */
public class AddDishesController extends ConnectDB {

    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField costField;

    @FXML
    private Label pathLabel;

    /**
     * Opens a file chooser dialog to upload an image file.
     * The selected file's absolute path is displayed in the UI.
     *
     * @throws IOException if the file cannot be read.
     */
    @FXML
    private void UploadFile() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choisissez un fichier");
        File selectedFile = chooser.showOpenDialog(null);
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath().replace("\\", "\\\\");
            pathLabel.setText(path);
        }
    }

    /**
     * Handles the submission of the dish creation form.
     * Validates the fields and inserts the data into the 'plats' table.
     *
     * @throws SQLException if a database error occurs.
     * @throws IOException  if navigation back to the menu fails.
     */
    @FXML
    private void handleSubmit() throws SQLException, IOException {
        String name = nameField.getText();

        if (name.length() >= 100) {
            showAlert(Alert.AlertType.ERROR, "Erreur ⚠", "Nom trop long.");
        } else if (!priceField.getText().matches("\\b\\d+.\\d{2}\\b")) {
            showAlert(Alert.AlertType.ERROR, "Erreur ⚠", "Le format du prix doit précisement être de la forme : 10.99");
        } else if (!costField.getText().matches("\\b\\d+.\\d{2}\\b")) {
            showAlert(Alert.AlertType.ERROR, "Erreur ⚠", "Le format du coût doit précisement être de la forme : 10.99");
        } else {
            String desc = descriptionField.getText();
            Double price = Double.valueOf(priceField.getText());

            if (name.isEmpty() || desc.isEmpty() || price <= 0 || pathLabel.getText().isEmpty() || costField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur ⚠", "Il faut remplir tous les champs.");
            } else {
                Connection connect = getConnection();
                Statement statement = connect.createStatement();
                statement.executeUpdate(
                        "INSERT INTO `plats` (`nom`, `description`, `prix`,`image`, `cout`) VALUES ('"
                                + name + "', '"
                                + desc + "', '"
                                + price + "','"
                                + pathLabel.getText() + "','"
                                + Double.valueOf(costField.getText()) + "')");
                showAlert(Alert.AlertType.INFORMATION, "Succès ✅", "Plat ajouté avec succès.");
                nameField.clear();
                descriptionField.clear();
                priceField.clear();
                costField.clear();
                pathLabel.setText("");
            }
        }
    }

    /**
     * Navigates back to the main menu from the current view.
     *
     * @param event The action event triggering the navigation.
     * @throws IOException if the main menu cannot be loaded.
     */
    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }

    /**
     * Utility method to show an alert dialog.
     *
     * @param type    Type of the alert.
     * @param title   Title of the dialog.
     * @param message Message to display.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
