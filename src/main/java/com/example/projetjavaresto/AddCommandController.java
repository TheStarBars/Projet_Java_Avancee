package com.example.projetjavaresto;

import Class.Plat;
import Class.Table;
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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static Utils.ConnectDB.getConnection;
import static Utils.ReturnMainMenu.MainMenu;

public class AddCommandController {

    @FXML
    private ListView<Plat> DishesListView; // ✅ maintenant typée avec Plat

    @FXML
    private ComboBox<String> DishesComboBox;

    @FXML
    private ListView<String> CommandListView;
    @FXML
    private Button AddCommandButton;

    private List<Plat> plats = new ArrayList<>(); // on la garde pour les détails

    private List<Plat> CommandList = new ArrayList<>();

    public String tableValue;

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

        statement.close();

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
        loadFreeTablesIntoComboBox();
    }

    private void loadFreeTablesIntoComboBox() throws SQLException {
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM tables;");

        List<Table> tables = new ArrayList<>();
        while (res.next()) {
            tables.add(new Table(
                    res.getInt("id"),
                    res.getInt("taille"),
                    res.getString("statut")
            ));
        }

        List<Table> freeTables = tables.stream()
                .filter(table -> Objects.equals(table.getStatus(), "Libre"))
                .collect(Collectors.toList());

        System.out.println(freeTables);

        // Clear previous items before adding new ones
        DishesComboBox.getItems().clear();
        freeTables.forEach(table -> DishesComboBox.getItems().add(table.getId()));
    }


    public int HandleTableSelection() throws SQLException {
        DishesComboBox.getSelectionModel().getSelectedItem();
        tableValue = DishesComboBox.getValue();
        System.out.println(tableValue);

        return Integer.parseInt(tableValue);

    }

    public void MakeCommand() throws SQLException {
        List<Plat> platsList = CommandList.stream()
                .collect(Collectors.toList());

        int table = HandleTableSelection();

        Gson gson = new Gson();
        String json = gson.toJson(platsList);
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        System.out.println(json);

        statement.executeUpdate(
                "INSERT INTO `commandes` (`id_table`, `liste_plats`, `statut`) VALUES ('"
                        + table + "', '"
                        + json.replace("'", "\\'").replace("\\", "\\\\") + "', '" // Échappement de '
                        + "En préparation" + "')");


        statement.executeUpdate(
                "UPDATE `tables` SET `statut` = 'Occupée' WHERE `id` = " + table);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        CommandList.clear();
        platsList.clear();
        CommandListView.getItems().clear();
        loadFreeTablesIntoComboBox();
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