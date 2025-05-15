package com.example.projetjavaresto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import Class.Employe;
import Class.Plat;
import Class.Commande;
import Class.Table;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

import static Utils.ConnectDB.getConnection;

public class ControlPanelController {
    @FXML
    private ListView<String> ControlListView;

    @FXML
    private Button valueOne;
    @FXML
    private Button valueTwo;
    @FXML
    private Button valueThree;
    @FXML
    private Button valueFour;



    private List<Commande> commandes = new ArrayList<>();



    private List<Employe> employes = new ArrayList<>();



//    public void DeliverdCommand() throws SQLException {
//
//        Connection connection = getConnection();
//        Statement statement = connection.createStatement();
//        String query = "select * from commandes";
//        ResultSet rs = statement.executeQuery(query);
//        Gson gson = new Gson();
//        Type platListType = new TypeToken<ArrayList<Plat>>(){}.getType();
//        while (rs.next()) {
//            String json = rs.getString("liste_plats");
//            ArrayList<Plat> listePlats = gson.fromJson(json, platListType);
//
//            commandes.add(new Commande(
//                    rs.getInt("id"),
//                    rs.getString("statut"),
//                    listePlats,
//                    rs.getInt("id_table"),
//                    rs.getTimestamp("date_heure_service")
//            ));
//        }
//
//        List<String> platsList = commandes.stream().filter((C)-> Objects.equals(C.getStatus(), "preparee"))
//                .filter((Table table, Commande c) -> Integer.parseInt(table.getId() == c.getTableArrayList()).collect(Collectors.toList());
//        ControlListView.setItems(FXCollections.observableList(platsList));
//
//    }

    public void employeByAge(javafx.event.ActionEvent event) throws SQLException {
        employes.clear();
        List<String> employesFiltered = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from employe";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            employes.add(
                    new Employe(
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getString("poste"),
                            resultSet.getInt("nb_heure_travaillee"),
                            resultSet.getInt("age")
                    )
            );
        }
        if (event.getSource() == valueOne) {
            employesFiltered = employes.stream()
                    .filter(E -> Integer.parseInt(E.getAge()) < 35)
                    .map(E -> E.getName() + " - " + E.getAge())
                    .collect(Collectors.toList());
        }else if (event.getSource() == valueTwo) {
            employesFiltered = employes.stream()
                    .filter(E -> Integer.parseInt(E.getAge()) > 35)
                    .map(E -> E.getName() + " - " + E.getAge())
                    .collect(Collectors.toList());
        }else if (event.getSource() == valueThree) {
            employesFiltered = employes.stream()
                    .filter(E -> Integer.parseInt(E.getAge()) < 45)
                    .map(E -> E.getName() + " - " + E.getAge())
                    .collect(Collectors.toList());
        }else if (event.getSource() == valueFour) {

            employesFiltered = employes.stream()
                    .filter(E -> Integer.parseInt(E.getAge()) > 45)
                    .map(E -> E.getName() + " - " + E.getAge())
                    .collect(Collectors.toList());
        }
        ControlListView.setItems(FXCollections.observableList(employesFiltered));

    }
}
