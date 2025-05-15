package com.example.projetjavaresto;

import Class.Plat;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Utils.ConnectDB.getConnection;
import static Utils.ReturnMainMenu.MainMenu;

public class ListDishesController {

    @FXML
    private ListView<Plat> DishesListView; // ✅ maintenant typée avec Plat

    @FXML
    private Button ReturnButton;

    private List<Plat> plats = new ArrayList<>(); // on la garde pour les détails

    @FXML
    public void initialize() throws SQLException {
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Plats;");

        while (rs.next()) {
            plats.add(new Plat(
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getDouble("prix"),
                    rs.getDouble("cout"),
                    rs.getString("image")
            ));
        }

        List<Plat> platsList = plats.stream().collect(Collectors.toList());

        DishesListView.setItems(FXCollections.observableList(platsList));

        // ✅ Custom affichage dans la ListView
        DishesListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Plat plat, boolean empty) {
                super.updateItem(plat, empty);
                if (empty || plat == null) {
                    setText(null);
                } else {
                    setText(plat.getName() + " - " + plat.getPrice() + "€");
                }
            }
        });

        // ✅ Gérer le clic (simple ou double selon ton besoin)
        DishesListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Plat selectedPlat = DishesListView.getSelectionModel().getSelectedItem();
                if (selectedPlat != null) {
                    showPlatPopup(selectedPlat);
                }
            }
        });
    }

    // ✅ Affiche les détails dans une popup
    private void showPlatPopup(Plat plat) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Détails du plat");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.getChildren().addAll(
                new Label("Nom : " + plat.getName()),
                new Label("Description : " + plat.getDescription()),
                new Label("Prix : " + String.format("%.2f€", plat.getPrice())),
                new Label("Cout de fabriquation :" + String.format("%.2f€", plat.getCost())),
                new ImageView("file:" + plat.getImage()) // tu peux utiliser ImageView si tu veux l’afficher
        );

        Scene scene = new Scene(vbox);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }


}
