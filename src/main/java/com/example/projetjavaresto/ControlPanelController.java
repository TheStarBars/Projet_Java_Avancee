package com.example.projetjavaresto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import Class.Employe;
import Class.Plat;
import Class.Commande;
import Class.Table;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import static Utils.ConnectDB.getConnection;

public class ControlPanelController {
    @FXML
    private ListView<String> ControlListView;
    @FXML
    private ListView<String> ResarchResultListView;
    @FXML
    private Button valueOne;
    @FXML
    private Button valueTwo;
    @FXML
    private Button valueThree;
    @FXML
    private Button valueFour;
    @FXML
    private Button DeliveredButton;
    @FXML
    private Button PreparationButton;
    @FXML
    private Button SearchButton;
    @FXML
    private Label MostLabel;
    @FXML
    private Label LeastLabel;
    @FXML
    private Label TotalLabel;
    @FXML
    private TextField SearchBarField;

    private List<Commande> commandes = new ArrayList<>();
    private List<Employe> employes = new ArrayList<>();
    private List<Table> tables = new ArrayList<>();
    private List<Plat> plats = new ArrayList<>(); // on la garde pour les détails

    @FXML
    public void initialize() throws SQLException {
            DishesCost();
    }

    public void DeliverdCommand(javafx.event.ActionEvent event) throws SQLException {
        commandes.clear();
        List<String> commandesFiltrees = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from commandes";
        ResultSet rs = statement.executeQuery(query);

        Statement statement2 = connection.createStatement();
        String query2 = "select * from tables";
        ResultSet rs2 = statement2.executeQuery(query2);

        while (rs2.next()) {
            tables.add(new Table(
                    rs2.getInt("id"),
                    rs2.getInt("taille"),
                    rs2.getString("statut")
            ));
        }

        List<Table> freeTableList = tables.stream()
                .filter((C) -> Objects.equals(C.getStatus(), "Occupée"))
                .collect(Collectors.toList());

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
    if (event.getSource() == DeliveredButton) {
    commandesFiltrees = commandes.stream()
            .filter(c -> "preparee".equals(c.getStatus()))
            .filter(c -> {
                Table table = freeTableList.stream()
                        .filter(t -> Integer.parseInt(t.getId()) == c.getTableArrayList())
                        .findFirst()
                        .orElse(null);
                return table != null && table.getStatus().equals("Occupée");
            }).map(c -> "Table n°" + c.getTableArrayList() + " - " + c.getStatus())
            .limit(5)
            .collect(Collectors.toList());
        }
        ControlListView.setItems(FXCollections.observableList(commandesFiltrees));
    }

    public void PreparationCommand(javafx.event.ActionEvent event) throws SQLException {
        commandes.clear();
        List<String> commandesFiltrees = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from commandes";
        ResultSet rs = statement.executeQuery(query);

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
        if (event.getSource() == PreparationButton) {
            commandesFiltrees = commandes.stream()
                    .filter(c -> "En préparation".equals(c.getStatus()))
                    .sorted((c1, c2) -> {
                        String nomPlat1 = c1.getPlatArrayList().isEmpty() ? "" : c1.getPlatArrayList().get(0).getName();
                        String nomPlat2 = c2.getPlatArrayList().isEmpty() ? "" : c2.getPlatArrayList().get(0).getName();
                        int compareNom = nomPlat1.compareTo(nomPlat2);
                        return compareNom != 0 ? compareNom : c1.getDateHeureService().compareTo(c2.getDateHeureService());
                    })
                    .map(c -> "Table n°" + c.getTableArrayList() + " - " + c.getStatus())
                    .collect(Collectors.toList());
        }
        ControlListView.setItems(FXCollections.observableList(commandesFiltrees));
    }

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

    public void DishesCost() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from plats";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            plats.add(new Plat(
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getDouble("prix"),
                    rs.getDouble("cout"),
                    rs.getString("image")
            ));
            plats.stream()
                    .max(Comparator.comparingDouble(Plat::getPrice))
                    .ifPresent(plat -> MostLabel.setText(plat.getName() + " : " + String.format("%.2f€", plat.getPrice())));

            plats.stream()
                    .min(Comparator.comparingDouble(Plat::getPrice))
                    .ifPresent(plat -> LeastLabel.setText(plat.getName() + " : " + String.format("%.2f€", plat.getPrice())));

            double total = plats.stream()
                    .mapToDouble(Plat::getPrice)
                    .sum();
            TotalLabel.setText(String.format("%.2f€", total));

        }
    }

    public void SearchBar() throws SQLException {
        String res = SearchBarField.getText().toLowerCase();
        plats.clear();
        if (res.trim().isEmpty()) {
            ResarchResultListView.getItems().clear();
            return;
        }
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String query = "select * from plats";
        ResultSet rs = statement.executeQuery(query);
        List<String> research = null;
        while (rs.next()) {
            plats.add(new Plat(
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getDouble("prix"),
                    rs.getDouble("cout"),
                    rs.getString("image")
            ));
            research = plats.stream()
                    .filter(plat -> plat.getDescription().toLowerCase().contains(res))
                    .map(Plat::getName)
                    .collect(Collectors.toList());
        }
        ResarchResultListView.setItems(FXCollections.observableList(research));
    }

}
