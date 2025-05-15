package com.example.projetjavaresto;

import Class.Plat;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.colors.ColorConstants;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Utils.ConnectDB.getConnection;

public class GeneratePdfController {

    @FXML
    private ListView<String> listView;

    @FXML
    private Button generatePdfButton;

    @FXML
    private Label revenueLabel;

    private List<Plat> allPlats = new ArrayList<>();

    @FXML
    public void initialize() {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT liste_plats FROM commandes");

            double total = 0;
            while (rs.next()) {
                String json = rs.getString("liste_plats");

                // Désérialisation avec Gson
                com.google.gson.reflect.TypeToken<List<Plat>> token = new com.google.gson.reflect.TypeToken<List<Plat>>() {};
                List<Plat> plats = new com.google.gson.Gson().fromJson(json, token.getType());

                for (Plat plat : plats) {
                    allPlats.add(plat);
                    total += plat.getPrice();
                    total -= plat.getCost();
                }
            }

            // Affichage dans la ListView
            List<String> display = allPlats.stream()
                    .map(p -> p.getName() + " - " + String.format("%.2f €", p.getPrice())+ " - " + String.format("%.2f €", p.getCost()))
                    .collect(Collectors.toList());
            listView.setItems(FXCollections.observableList(display));

            revenueLabel.setText("Total: " + String.format("%.2f €", total));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void generatePDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(generatePdfButton.getScene().getWindow());

        if (file != null) {
            try (PdfWriter writer = new PdfWriter(new FileOutputStream(file));
                 PdfDocument pdf = new PdfDocument(writer);
                 Document document = new Document(pdf)) {

                document.add(new Paragraph("Recette du restaurant").setFontSize(16));
                document.add(new Paragraph(""));

                double total = 0;
                for (Plat plat : allPlats) {
                    document.add(new Paragraph(plat.getName() + " - " + String.format("%.2f €", plat.getPrice())).setFontColor(ColorConstants.GREEN));
                    document.add(new Paragraph(plat.getName() + " - " + String.format("%.2f €", plat.getCost())).setFontColor(ColorConstants.RED));
                    total += plat.getPrice();
                    total -= plat.getCost();
                }

                document.add(new Paragraph(""));
                document.add(new Paragraph("Total: " + String.format("%.2f €", total)).setFontSize(16));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
