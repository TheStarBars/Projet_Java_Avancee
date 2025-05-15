package com.example.projetjavaresto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import Class.Commande;
import Class.Plat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static Utils.ConnectDB.getConnection;
import static Utils.ReturnMainMenu.MainMenu;

/**
 * Controller for displaying and managing the list of restaurant orders.
 * <p>
 * Handles loading orders from the database, displaying them in a ListView,
 * updating order statuses, showing detailed order information in a popup,
 * and returning to the main menu.
 */
public class ListCommandController {

    @FXML
    private ListView<Commande> CommandeListView;
    @FXML
    private Button preparee;
    @FXML
    private Button annulee;

    private List<Commande> commandes = new ArrayList<>();

    /**
     * Initializes the controller by loading order data, setting custom cell rendering,
     * and adding double-click event handling to show order details.
     *
     * @throws SQLException if database access error occurs
     */
    @FXML
    public void initialize() throws SQLException {
        reloadCommandData();

        CommandeListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Commande commande, boolean empty) {
                super.updateItem(commande, empty);
                if (empty || commande == null) {
                    setText(null);
                } else {
                    setText("Table n°" + commande.getTableId() + " " + commande.getStatus());
                }
            }
        });

        CommandeListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Commande selectedCommande = CommandeListView.getSelectionModel().getSelectedItem();
                if (selectedCommande != null) {
                    showPlatPopup(selectedCommande);
                }
            }
        });
    }

    /**
     * Reloads the list of commandes from the database and filters
     * those with status "En préparation" to display in the ListView.
     *
     * @throws SQLException if database access error occurs
     */
    private void reloadCommandData() throws SQLException {
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM commandes;");

        Gson gson = new Gson();
        Type platListType = new TypeToken<ArrayList<Plat>>() {}.getType();

        commandes.clear();
        while (rs.next()) {
            String json = rs.getString("liste_plats");
            ArrayList<Plat> listePlats = gson.fromJson(json, platListType);

            commandes.add(new Commande(
                    rs.getInt("id"),
                    rs.getString("statut"),
                    listePlats,
                    rs.getInt("id_table"),
                    rs.getTimestamp("date_heure_service")
            ));
        }

        List<Commande> filteredList = commandes.stream()
                .filter(c -> Objects.equals(c.getStatus(), "En préparation"))
                .collect(Collectors.toList());

        CommandeListView.setItems(FXCollections.observableList(filteredList));
    }

    /**
     * Displays a modal popup window showing detailed information
     * about the selected order, including table number, status,
     * list of dishes, and order timestamp.
     *
     * @param commande the selected order to display
     */
    private void showPlatPopup(Commande commande) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Détails de la commande");

        String dishNames = commande.getPlatArrayList().stream()
                .map(Plat::getName)
                .collect(Collectors.joining(", "));

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.getChildren().addAll(
                new Label("Table n° : " + commande.getTableId()),
                new Label("Statut : " + commande.getStatus()),
                new Label("Liste des plats : " + dishNames),
                new Label("Date/Heure de la commande : " + commande.getDateHeureService().toString())
        );

        Scene scene = new Scene(vbox);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    /**
     * Updates the status of the selected order in the database based on
     * which button triggered the event ("preparee" or "annulee"), then reloads the list.
     *
     * @param event the action event triggered by clicking a button
     * @throws SQLException if database access error occurs
     */
    @FXML
    private void UpdateStatut(javafx.event.ActionEvent event) throws SQLException {
        Commande selectedCommande = CommandeListView.getSelectionModel().getSelectedItem();
        if (selectedCommande == null) return;

        int id = selectedCommande.getId();
        Connection connect = getConnection();
        Statement statement = connect.createStatement();

        if (event.getSource() == preparee) {
            statement.executeUpdate("UPDATE commandes SET statut = 'preparee' WHERE id = " + id + ";");
        } else if (event.getSource() == annulee) {
            statement.executeUpdate("UPDATE commandes SET statut = 'annulee' WHERE id = " + id + ";");
        }
        reloadCommandData();
    }

    /**
     * Returns the user to the main menu by switching scenes.
     *
     * @param event the action event triggered by clicking the return button
     * @throws IOException if an error occurs during scene change
     */
    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }
}
