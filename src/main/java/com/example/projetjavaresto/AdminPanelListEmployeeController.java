package com.example.projetjavaresto;

import Class.Employe;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Utils.ConnectDB.getConnection;
import static Utils.ReturnMainMenu.AdminMenu;

/**
 * Controller for the admin panel that displays and manages the list of employees.
 */
public class AdminPanelListEmployeeController {

    @FXML
    private ListView<Employe> EmployeListView;
    @FXML
    private TextField HourField;

    private List<Employe> employes = new ArrayList<>();

    /**
     * Initializes the controller by loading employees from the database
     * and configuring the ListView for display and interaction.
     *
     * @throws SQLException if a database error occurs
     */
    @FXML
    public void initialize() throws SQLException {
        getEmployes();

        EmployeListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Employe employe, boolean empty) {
                super.updateItem(employe, empty);
                if (empty || employe == null) {
                    setText(null);
                } else {
                    setText(employe.getName() + " --- " + employe.getPost());
                }
            }
        });

        EmployeListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Employe selectedEmploye = EmployeListView.getSelectionModel().getSelectedItem();
                if (selectedEmploye != null) {
                    showPlatPopup(selectedEmploye);
                }
            }
        });
    }

    /**
     * Retrieves all employees from the database and displays them in the ListView.
     *
     * @throws SQLException if a database error occurs
     */
    private void getEmployes() throws SQLException {
        employes.clear();
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM employe;");

        while (rs.next()) {
            employes.add(new Employe(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("poste"),
                    rs.getInt("nb_heure_travaillee"),
                    rs.getInt("age")
            ));

            List<Employe> employeList = employes.stream().collect(Collectors.toList());
            EmployeListView.setItems(FXCollections.observableList(employeList));
        }
    }

    /**
     * Displays a popup window showing the selected employee's details.
     *
     * @param employe the selected employee
     */
    private void showPlatPopup(Employe employe) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Employee Details");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.getChildren().addAll(
                new Label("Name: " + employe.getName()),
                new Label("Position: " + employe.getPost()),
                new Label("Worked Hours: " + employe.getWorkedHour()),
                new Label("Age: " + employe.getAge())
        );

        Scene scene = new Scene(vbox);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    /**
     * Deletes the selected employee from the database.
     *
     * @param event the action event triggered by the delete button
     * @throws SQLException if a database error occurs
     */
    @FXML
    private void DeleteEmploye(javafx.event.ActionEvent event) throws SQLException {
        Employe selectedEmploye = EmployeListView.getSelectionModel().getSelectedItem();
        int id = selectedEmploye.getId();
        if (id != 0) {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();
            statement.executeUpdate("DELETE FROM employe WHERE id = " + id + ";");
        }
        getEmployes();
    }

    /**
     * Adds worked hours to the selected employee and updates the database.
     *
     * @param event the action event triggered by the add hours button
     * @throws SQLException if a database error occurs
     */
    @FXML
    private void AddHour(javafx.event.ActionEvent event) throws SQLException {
        Employe selectedEmploye = EmployeListView.getSelectionModel().getSelectedItem();
        int hours = Integer.parseInt(HourField.getText());
        int id = selectedEmploye.getId();

        if (id != 0) {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT nb_heure_travaillee FROM employe WHERE id = " + id + ";");

            if (rs.next()) {
                int hoursWorked = rs.getInt("nb_heure_travaillee");
                int totalHours = hoursWorked + hours;
                statement.executeUpdate("UPDATE employe SET nb_heure_travaillee = " + totalHours + " WHERE id = " + id + ";");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Hours added successfully");
                alert.showAndWait();
            }
        }

        HourField.clear();
        getEmployes();
    }

    /**
     * Navigates back to the admin main menu.
     *
     * @param event the action event triggered by the return button
     * @throws IOException if the menu view cannot be loaded
     */
    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminMenu(stage);
    }
}

