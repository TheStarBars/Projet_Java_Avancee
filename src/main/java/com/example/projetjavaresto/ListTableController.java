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

/**
 * Controller class for managing the list of tables in the restaurant.
 * It handles displaying free and occupied tables, updating table status,
 * and showing detailed information about a selected table.
 */
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

    /**
     * Initializes the controller by loading table data,
     * setting up custom cell factories, and handling double-click events.
     *
     * @throws SQLException if a database access error occurs
     */
    @FXML
    public void initialize() throws SQLException {
        reloadTableData();

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

        FreeTableListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Table selectedTable = FreeTableListView.getSelectionModel().getSelectedItem();
                if (selectedTable != null) {
                    showPlatPopup(selectedTable);
                }
            }
        });
    }

    /**
     * Loads the table data from the database and updates the list views
     * for free and occupied tables.
     *
     * @throws SQLException if a database access error occurs
     */
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
                .filter(table -> Objects.equals(table.getStatus(), "Libre"))
                .collect(Collectors.toList());
        FreeTableListView.setItems(FXCollections.observableList(freeTableList));

        List<Table> occupedTableList = tables.stream()
                .filter(table -> Objects.equals(table.getStatus(), "Occupée"))
                .collect(Collectors.toList());
        OccupedTableListView.setItems(FXCollections.observableList(occupedTableList));
    }

    /**
     * Shows a popup window displaying detailed information about a table.
     *
     * @param table the table whose details are to be shown
     */
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

    /**
     * Updates the status of the selected occupied table to 'Libre' (free)
     * and reloads the table data.
     *
     * @param event the action event triggered by the button
     * @throws SQLException if a database access error occurs
     */
    @FXML
    private void UpdateStatut(javafx.event.ActionEvent event) throws SQLException {
        Table selectedTable = OccupedTableListView.getSelectionModel().getSelectedItem();
        String strId = selectedTable.getId();
        int id = Integer.parseInt(strId);

        if (id != 0) {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();

            statement.executeUpdate("UPDATE tables SET statut = 'Libre' WHERE id = " + id + ";");

            reloadTableData();
        }
    }

    /**
     * Returns to the main menu by switching the current stage.
     *
     * @param event the action event triggered by the button
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }
}
