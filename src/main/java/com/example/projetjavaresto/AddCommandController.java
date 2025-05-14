package com.example.projetjavaresto;

import Class.Plat;
import Class.Commande;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Utils.ConnectDB.getConnection;
import static Utils.ReturnMainMenu.MainMenu;

public class AddCommandController {

    @FXML
    private ListView<Plat> DishesListView; // ✅ maintenant typée avec Plat

    @FXML
    private ListView<String> CommandListView;
    @FXML
    private Button AddCommandButton;

    private List<Plat> plats = new ArrayList<>(); // on la garde pour les détails

    private List<Plat> CommandList = new ArrayList<>();

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

        // ✅ Gérer le clic (simple ou double selon ton besoin
        DishesListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Plat selectedPlat = DishesListView.getSelectionModel().getSelectedItem();
                if (selectedPlat != null) {
                    CommandList.add(selectedPlat);
                    List<String> formattedList = CommandList.stream()
                            .map(plat -> plat.getName() + " - " + String.format("%.2f€", plat.getPrice()))
                            .collect(Collectors.toList());


                    CommandListView.setItems(FXCollections.observableList(formattedList));


                }
            }
        });
    }

    public void MakeCommand() throws SQLException {
        List<Plat> platsList = CommandList.stream()
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String json = gson.toJson(platsList);
        Connection connect = getConnection();
        Statement statement = connect.createStatement();

        statement.executeUpdate(
                "INSERT INTO `commandes` (`id_table`, `liste_plats`, `statut`) VALUES ('"
                        + 1 + "', '"
                        + json.replace("'", "\\'") + "', '" // Échappement de '
                        + "En préparation" + "')");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        CommandList.clear();
        platsList.clear();
        CommandListView.getItems().clear();
        alert.setTitle("Succès");
        alert.setHeaderText("Commande ajoutée avec succès");
        alert.showAndWait();



    }

    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }


}