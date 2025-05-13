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
    @FXML
    private ListView<String> DishesListView;

    @FXML
    public void initialize() throws SQLException {
        Connection connect = getConnection();
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Plats;");

        List<Plat> plats = new ArrayList<>();
        while (rs.next()) {
            plats.add(new Plat(rs.getString("nom"), rs.getString("description")
                    ,rs.getDouble("prix"), rs.getString("image")));
        }

        List<String> formattedPlats = plats.stream()
                .map(plat -> plat.getName() + " - " + plat.getPrice() + "€")
                .collect(Collectors.toList());

        DishesListView.setItems(FXCollections.observableList(formattedPlats));
    }
}
