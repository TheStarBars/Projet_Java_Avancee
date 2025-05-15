package com.example.projetjavaresto;

import Class.Plat;
import Class.Table;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static Utils.ConnectDB.getConnection;
import static Utils.ReturnMainMenu.MainMenu;

/**
 * Controller class responsible for handling the addition of a new order (command).
 * It allows the user to:
 * - View the list of available dishes.
 * - Select dishes and add them to an order.
 * - Choose a free table.
 * - Save the order to the database.
 */
public class AddCommandController {

    @FXML
    private ListView<Plat> DishesListView;
    @FXML
    private ComboBox<String> DishesComboBox;
    @FXML
    private ListView<String> CommandListView;

    private List<Plat> plats = new ArrayList<>();

    private List<Plat> CommandList = new ArrayList<>();

    public String tableValue;

    /**
     * Initializes the controller by loading the list of dishes and free tables.
     * Sets up the ListView cell rendering and click handler.
     *
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Loads the list of free tables from the database and populates the ComboBox.
     *
     * @throws SQLException if a database access error occurs
     */
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

        DishesComboBox.getItems().clear();
        freeTables.forEach(table -> DishesComboBox.getItems().add(String.valueOf(table.getId())));
    }

    /**
     * Retrieves the selected table ID from the ComboBox.
     *
     * @return the selected table ID as an integer
     * @throws SQLException if a database access error occurs
     */
    public int HandleTableSelection() throws SQLException {
        tableValue = DishesComboBox.getValue();
        return Integer.parseInt(tableValue);
    }

    /**
     * Saves the current command to the database by:
     * - Serializing the selected dishes as JSON.
     * - Inserting the command into the database.
     * - Marking the selected table as occupied.
     * - Displaying a confirmation alert.
     *
     * @throws SQLException if a database access error occurs
     */
    public void MakeCommand() throws SQLException {
        List<Plat> platsList = CommandList.stream().collect(Collectors.toList());
        int table = HandleTableSelection();

        Gson gson = new Gson();
        String json = gson.toJson(platsList);

        Connection connect = getConnection();
        Statement statement = connect.createStatement();

        statement.executeUpdate(
                "INSERT INTO commandes (id_table, liste_plats, statut) VALUES ('"
                        + table + "', '"
                        + json.replace("'", "\\'").replace("\\", "\\\\") + "', '"
                        + "En préparation" + "')"
        );

        statement.executeUpdate(
                "UPDATE tables SET statut = 'Occupée' WHERE id = " + table
        );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        CommandList.clear();
        platsList.clear();
        CommandListView.getItems().clear();
        loadFreeTablesIntoComboBox();
        alert.setTitle("Succès ✅");
        alert.setHeaderText("Commande ajoutée avec succès");
        alert.showAndWait();
    }

    /**
     * Returns to the main menu by changing the scene.
     *
     * @param event the action event triggered by the user
     * @throws IOException if an I/O error occurs during scene change
     */
    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }
}
