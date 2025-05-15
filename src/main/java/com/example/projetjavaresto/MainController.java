package com.example.projetjavaresto;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import Utils.Timer;

import java.io.IOException;

/**
 * Controller class for the main menu of the restaurant management application.
 * Handles navigation between views and displays a countdown timer.
 */
public class MainController extends Thread {

    @FXML
    public Label AddDishesLabel;
    @FXML
    public Label ListDishesLabel;
    @FXML
    public Label TimerLabel;

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

    private Timeline timeline;

    /**
     * Initializes the controller after the root element has been processed.
     * Starts the countdown timer display.
     */
    @FXML
    public void initialize() {
        startTimerDisplay();
    }

    /**
     * Starts a timeline that updates the timer label every second
     * with the remaining service time from the singleton Timer.
     */
    private void startTimerDisplay() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    int timeLeft = Timer.getInstance().getTimeLeft();
                    int minutes = timeLeft / 60;
                    int seconds = timeLeft % 60;
                    TimerLabel.setText(String.format("Temps restant : %02d:%02d", minutes, seconds));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Handles navigation to the appropriate view based on the clicked button.
     *
     * @param event the action event triggered by clicking a navigation button
     * @throws IOException if the FXML resource fails to load
     */
    public void NavigateTo(javafx.event.ActionEvent event) throws IOException {
        Stage stage = null;
        Parent myNewScene = null;

        if (event.getSource() == AddDishesButton) {
            stage = (Stage) AddDishesButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AddDishesView.fxml"));

        } else if (event.getSource() == ListDishesButton) {
            stage = (Stage) ListDishesButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("ListDishesView.fxml"));

        } else if (event.getSource() == AddCommandButton) {
            if (Timer.getInstance().canTakeCommande()) {
                stage = (Stage) AddCommandButton.getScene().getWindow();
                myNewScene = FXMLLoader.load(MainController.class.getResource("AddCommandView.fxml"));
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Timer dépasser");
                alert.setHeaderText(null);
                alert.setContentText("Votre temps de service est fini");
                alert.showAndWait();
                stage = (Stage) AddCommandButton.getScene().getWindow();
                myNewScene = FXMLLoader.load(MainController.class.getResource("MainView.fxml"));
            }

        } else if (event.getSource() == ListCommandButton) {
            stage = (Stage) ListCommandButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("ListCommandView.fxml"));

        } else if (event.getSource() == AddTableButton) {
            stage = (Stage) AddTableButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AddTableView.fxml"));

        } else if (event.getSource() == ListTableButton) {
            stage = (Stage) ListTableButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("ListTableView.fxml"));

        } else if (event.getSource() == AdminPanelButton) {
            stage = (Stage) AdminPanelButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AdminConnexionView.fxml"));
        }

        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("KrampTeckResto");
        stage.show();
    }
}
