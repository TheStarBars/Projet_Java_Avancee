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

/**
 * Controller class for displaying the list of dishes (Plats).
 * Loads data from the database, displays it in a ListView,
 * and shows a detailed popup when a dish is double-clicked.
 */
public class ListDishesController {

    @FXML
    private ListView<Plat> DishesListView;

    @FXML
    private Button ReturnButton;

    private final List<Plat> plats = new ArrayList<>();

    /**
     * Initializes the view by loading dishes from the database
     * and displaying them in a customized ListView.
     *
     * @throws SQLException if an error occurs while accessing the database.
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
                    showPlatPopup(selectedPlat);
                }
            }
        });
    }

    /**
     * Displays a popup window with detailed information about the selected dish.
     *
     * @param plat The selected Plat object.
     */
    private void showPlatPopup(Plat plat) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Dish Details");


        ImageView imageView = new ImageView("file:" + plat.getImage());
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.getChildren().addAll(
                new Label("Nom : " + plat.getName()),
                new Label("Description : " + plat.getDescription()),
                new Label("Prix : " + String.format("%.2f€", plat.getPrice())),
                new Label("Cout de fabriquation :" + String.format("%.2f€", plat.getCost())),
                imageView
        );

        Scene scene = new Scene(vbox);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    /**
     * Handles the action to return to the main menu.
     *
     * @param event The JavaFX ActionEvent triggered by the button click.
     * @throws IOException if the main menu view cannot be loaded.
     */
    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }
}

