package com.example.projetjavaresto;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import Class.Timer;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainController {
    @FXML
    public Label AddDishesLabel;
    @FXML
    public Label ListDishesLabel;
    @FXML
    public Button AddDishesButton;
    @FXML
    public Button ListDishesButton;
    @FXML
    public Button AddCommandButton;
    @FXML
    public Button ListCommandButton;
    @FXML
    public Button AddTableButton;
    @FXML
    public Button ListTableButton;

    @FXML
    public Button AdminPanelButton;

    @FXML
    public Label TimerLabel;

    @FXML
    private ImageView LogoView;

    private Timer timer;

    public void initialize() {
        LogoView.setImage(new Image(getClass().getResourceAsStream("/assets/Logo.png")));
        startTimer();
        updateUI();

    }

    public void startTimer() {
        timer = Timer.getInstance(25, () -> Platform.runLater(this::updateUI));

    }

    private void updateUI() {
        int seconds = timer.getTimeLeft();
        int minutesPart = seconds / 60;
        int secondsPart = seconds % 60;

        TimerLabel.setText(String.format("%02d:%02d", minutesPart, secondsPart));

        if (timer.getTimeLeft() <= 1 * 60) {
            TimerLabel.setStyle("-fx-text-fill: #D84A4A;"); // Red
        } else {
            TimerLabel.setStyle("-fx-text-fill: #FF914D;"); // Orange
        }
        System.out.println("Time left: " + timer.getTimeLeft() + " | canTakeCommande: " + timer.canTakeCommande());


    }


    public void NavigateTo(javafx.event.ActionEvent event ) throws IOException {
        Stage stage = null;
        Parent myNewScene = null;

        if (event.getSource() == AddDishesButton){
            stage = (Stage) AddDishesButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AddDishesView.fxml"));
        }else if (event.getSource() == ListDishesButton) {
            stage = (Stage) ListDishesButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("ListDishesView.fxml"));
        }else if (event.getSource() == AddCommandButton) {
            if (!timer.canTakeCommande()) {
                AddCommandButton.setDisable(true);
                AddCommandButton.setText("Commande fermée");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Timer dépasser");
                alert.setHeaderText(null);
                alert.setContentText("Votre temps de service est fini");
                alert.showAndWait();
                System.out.println("Trop tard pour commander");
                return;
            }
            stage = (Stage) AddCommandButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AddCommandView.fxml"));
        }else if (event.getSource() == ListCommandButton) {
            stage = (Stage) ListCommandButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("ListCommandView.fxml"));
        }else if (event.getSource() == AddTableButton) {
            stage = (Stage) AddTableButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AddTableView.fxml"));
        }else if (event.getSource() == ListTableButton) {
            stage = (Stage) ListTableButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("ListTableView.fxml"));
        }else if (event.getSource() == AdminPanelButton) {
            stage = (Stage) AdminPanelButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AdminConnexionView.fxml"));
        }

        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("KrampTeckResto");
        stage.show();

    }

}
