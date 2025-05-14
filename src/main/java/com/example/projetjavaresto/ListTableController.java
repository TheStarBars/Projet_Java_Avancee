package com.example.projetjavaresto;

import Class.Table;
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

public class ListTableController {

    @FXML
    private ListView<Table> FreeTableListView;

    @FXML
    private ListView<Table> OccupedTableListView;



    @FXML
    private Button preparee;

    @FXML
    private Button annulee;


    private List<Table> tables = new ArrayList<>();

    @FXML
    public void initialize() throws SQLException {
        reloadTableData();

        // Affichage personnalisé
        FreeTableListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Table table, boolean empty) {
                super.updateItem(table, empty);
                if (empty || table == null) {
                    setText(null);
                } else {
                    setText("Table n°" + table.getId());
                }
            }
        });

        OccupedTableListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Table table, boolean empty) {
                super.updateItem(table, empty);
                if (empty || table == null) {
                    setText(null);
                } else {
                    setText("Table n°" + table.getId());
                }
            }
        });

        // Gestion du double-clic
        FreeTableListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Table selectedTable = FreeTableListView.getSelectionModel().getSelectedItem();
                if (selectedTable != null) {
                    showPlatPopup(selectedTable);
                }
            }
        });
    }

    private void reloadTableData() throws SQLException {
        tables.clear();
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM tables;");

        while (rs.next()) {
            tables.add(new Table(
                    rs.getInt("id"),
                    rs.getInt("taille"),
                    rs.getString("statut")
            ));
        }

        List<Table> freeTableList = tables.stream()
                .filter((C) -> Objects.equals(C.getStatus(), "Libre"))
                .collect(Collectors.toList());
        FreeTableListView.setItems(FXCollections.observableList(freeTableList));

        List<Table> occupedTableList = tables.stream()
                .filter((C) -> Objects.equals(C.getStatus(), "Occupée"))
                .collect(Collectors.toList());
        OccupedTableListView.setItems(FXCollections.observableList(occupedTableList));
    }

    // Affiche les détails dans une popup
    private void showPlatPopup(Table table) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Détails de la Table");


        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.getChildren().addAll(
                new Label("Table n° : " + table.getId()),
                new Label("Taille : " + table.getSize()),
                new Label("Statut : " + table.getStatus())
        );

        Scene scene = new Scene(vbox);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    @FXML
    private void UpdateStatut(javafx.event.ActionEvent event) throws SQLException {
        Table selectedTable = OccupedTableListView.getSelectionModel().getSelectedItem();
        String strId = selectedTable.getId();
        Integer id = Integer.parseInt(strId);

        if (id != 0) {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();

            statement.executeUpdate("UPDATE tables SET statut = 'Libre' WHERE id = " + id + ";");

            reloadTableData();
        }
    }

    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }
}
