package com.example.projetjavaresto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static Utils.ReturnMainMenu.MainMenu;

/**
 * Controller class for the admin panel menu of the application.
 * Handles navigation between various administrative features.
 */
public class AdminPanelController {
    @FXML
    public Button EmployeeAddButton;
    @FXML
    public Button EmployeeListButton;
    @FXML
    public Button GeneratePdfButton;
    @FXML
    public Button ControlPanelButton;

    /**
     * Handles navigation to different views based on the clicked button.
     *
     * @param event the action event triggered by a button click
     * @throws IOException if the FXML file cannot be loaded
     */
    public void NavigateTo(javafx.event.ActionEvent event) throws IOException {
        Stage stage = null;
        Parent myNewScene = null;

        if (event.getSource() == EmployeeListButton) {
            stage = (Stage) EmployeeListButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AdminPanelAddEmployeeView.fxml"));
        } else if (event.getSource() == EmployeeAddButton) {
            stage = (Stage) EmployeeAddButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AdminPanelListEmployeeView.fxml"));
        } else if (event.getSource() == GeneratePdfButton) {
            stage = (Stage) GeneratePdfButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("GeneratePdfView.fxml"));
        } else if (event.getSource() == ControlPanelButton) {
            stage = (Stage) ControlPanelButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("ControlPanelView.fxml"));
        }

        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("KrampTeckResto");
        stage.show();
    }

    /**
     * Returns to the main menu of the application.
     *
     * @param event the action event triggered by a button click
     * @throws IOException if the main menu view cannot be loaded
     */
    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }
}
