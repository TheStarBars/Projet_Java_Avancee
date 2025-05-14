package com.example.projetjavaresto;

import Class.Employe;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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

public class AdminPanelListEmployeeController {


    @FXML
    private ListView<Employe> EmployeListView; // ✅ maintenant typée avec Plat

    @FXML
    private TextField HourField;

    private List<Employe> employes = new ArrayList<>(); // on la garde pour les détails

    @FXML
    public void initialize() throws SQLException {
        getEmployes();

        // ✅ Custom affichage dans la ListView
        EmployeListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Employe employe, boolean empty) {
                super.updateItem(employe, empty);
                if (empty || employe == null) {
                    setText(null);
                } else {
                    setText(employe.getName() + " --- " + employe.getPost() );
                }
            }
        });

        // ✅ Gérer le clic (simple ou double selon ton besoin)
        EmployeListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Employe selectedEmploye = EmployeListView.getSelectionModel().getSelectedItem();
                if (selectedEmploye != null) {
                    showPlatPopup(selectedEmploye);
                }
            }
        });
    }

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

    // ✅ Affiche les détails dans une popup
    private void showPlatPopup(Employe employe) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Détails du plat");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.getChildren().addAll(
                new Label("Nom : " + employe.getName()),
                new Label("Poste : " + employe.getPost()),
                new Label("Nombre d'heure travailler : " + employe.getWorkedHour()),
                new Label("Age :" + employe.getAge())
        );

        Scene scene = new Scene(vbox);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    @FXML
    private void DeleteEmploye(javafx.event.ActionEvent event) throws SQLException {
        Employe selectedEmploye = EmployeListView.getSelectionModel().getSelectedItem();
        int id = selectedEmploye.getId();
        if (id != 0){
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        statement.executeUpdate("DELETE FROM employe WHERE id = "+ id +"  ;");
        }
        getEmployes();

    }

    @FXML
    private void AddHour(javafx.event.ActionEvent event) throws SQLException {
        Employe selectedEmploye = EmployeListView.getSelectionModel().getSelectedItem();
        int hours = Integer.parseInt(HourField.getText());
        int id = selectedEmploye.getId();
        if (id != 0){
            Connection connect = getConnection();
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("SELECT nb_heure_travaillee FROM employe WHERE id = "+ id +";");

            if (rs.next()) {
                int hoursWorked = rs.getInt("nb_heure_travaillee");
                int totalHours = hoursWorked + hours;
                statement.executeUpdate("UPDATE employe SET nb_heure_travaillee = " + totalHours + " WHERE id = "+ id +";");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Ajouter avec succes");
                alert.showAndWait();
            }
        }
        HourField.clear();
        getEmployes();

    }

    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AdminMenu(stage);
    }
}
