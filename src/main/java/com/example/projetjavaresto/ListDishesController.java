package com.example.projetjavaresto;

import Utils.ConnectDB;
import Class.Plat;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Utils.ConnectDB.getConnection;


public class ListDishesController {
    private ArrayList<Plat> dishesList = new ArrayList<>();
    @FXML
    private ListView<String> DishesListView;

    @FXML
    public void initialize() throws SQLException {
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Plats;");

        List<String> formattedPlats = new ArrayList<>();

        while (rs.next()) {
            String nom = rs.getString("nom");
            double prix = rs.getDouble("prix");
            formattedPlats.add(nom + " - " + prix + "€");
        }

        DishesListView.setItems(FXCollections.observableList(formattedPlats));
    }
}
