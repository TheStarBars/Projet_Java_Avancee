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

/**
 * Controller for the Admin Panel that handles adding new employees to the system.
 */
public class AdminPanelAddEmployeeController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField postField;
    @FXML
    private TextField ageField;

    /**
     * Handles form submission for adding a new employee.
     * Validates inputs and inserts the employee into the database if validation passes.
     *
     * @throws SQLException if a database error occurs
     * @throws IOException  if the database connection fails
     */
    @FXML
    private void handleSubmit() throws SQLException, IOException {
        String name = nameField.getText();
        String post = postField.getText();
        String age = ageField.getText();

        if (name.length() >= 100) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ⚠");
            alert.setHeaderText("Nom trop long");
            alert.showAndWait();
        } else if (!age.matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ⚠");
            alert.setHeaderText("Veuillez entrer un age correcte");
            alert.showAndWait();
        } else if (post.length() >= 100) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ⚠");
            alert.setHeaderText("Le nom du poste est trop long");
            alert.showAndWait();
        }

        if (name.isEmpty() || age.isEmpty() || post.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ⚠");
            alert.setHeaderText("Tous les champs doivent être remplit");
            alert.showAndWait();
        } else {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `employe` (`nom`, `poste`, `nb_heure_travaillee`, `age`) VALUES ('"
                            + name + "', '" + post + "', '0', '" + Integer.parseInt(age) + "')"
            );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès ✅");
            alert.setHeaderText("Employé ajouté avec succès");
            alert.showAndWait();

            nameField.clear();
            postField.clear();
            ageField.clear();
        }
    }

    /**
     * Returns to the main admin menu.
     *
     * @param event the action event triggered by the return button
     * @throws IOException if the menu cannot be loaded
     */
    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminMenu(stage);
    }
}

