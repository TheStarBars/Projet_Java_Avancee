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

import java.awt.event.ActionEvent;
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

public class ListCommandController {

    @FXML
    private ListView<Commande> CommandeListView;

    @FXML
    private Button preparee;

    @FXML
    private Button annulee;

    private List<Commande> commandes = new ArrayList<>();

    @FXML
    public void initialize() throws SQLException {
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM commandes;");

        Gson gson = new Gson();
        Type platListType = new TypeToken<ArrayList<Plat>>(){}.getType();

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

        List<Commande> platsList = commandes.stream().filter((C)-> Objects.equals(C.getStatus(), "En préparation")).collect(Collectors.toList());
        CommandeListView.setItems(FXCollections.observableList(platsList));

        // Affichage personnalisé
        CommandeListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Commande commande, boolean empty) {
                super.updateItem(commande, empty);
                if (empty || commande == null) {
                    setText(null);
                } else {
                    setText("Table n°" + commande.getTableArrayList() + " " + commande.getStatus());
                }
            }
        });

        // Gestion du double-clic
        CommandeListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Commande selectedCommande = CommandeListView.getSelectionModel().getSelectedItem();
                if (selectedCommande != null) {
                    showPlatPopup(selectedCommande);
                }
            }
        });
    }

    // Affiche les détails dans une popup
    private void showPlatPopup(Commande commande) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Détails de la commande");

        String nomsDesPlats = commande.getPlatArrayList().stream()
                .map(Plat::getName) // Vérifie que ta méthode s'appelle bien getName()
                .collect(Collectors.joining(", "));

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.getChildren().addAll(
                new Label("Table n° : " + commande.getTableArrayList()),
                new Label("Statut : " + commande.getStatus()),
                new Label("Liste des plats : " + nomsDesPlats),
                new Label("Date/Heure de la commande : " + commande.getDateHeureService().toString())
        );

        Scene scene = new Scene(vbox);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    @FXML
    private void UpdateStatut(javafx.event.ActionEvent event) throws SQLException {
        Commande selectedCommande = CommandeListView.getSelectionModel().getSelectedItem();
        int id = selectedCommande.getId();
        if (id != 0){}
        Connection connect = getConnection();
        Statement statement = connect.createStatement();

        if (event.getSource() == preparee){
            statement.executeUpdate("UPDATE commandes SET statut = 'preparee' WHERE id = "+ id +"  ;");

        }else if (event.getSource() == annulee){
            statement.executeUpdate("UPDATE commandes SET statut = 'annulee' WHERE id = "+ id +"  ;");
        }
        CommandeListView.refresh();
    }

    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }
}
