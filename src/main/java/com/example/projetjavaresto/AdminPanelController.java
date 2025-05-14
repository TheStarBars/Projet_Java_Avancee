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

public class AdminPanelController {
    @FXML
    public Button EmployeeAddButton;
    @FXML
    public Button EmployeeListButton;

    public void NavigateTo(javafx.event.ActionEvent event ) throws IOException {
        Stage stage = null;
        Parent myNewScene = null;

        if (event.getSource() == EmployeeListButton){
            stage = (Stage) EmployeeListButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AdminPanelAddEmployeeView.fxml"));
        }else if (event.getSource() == EmployeeAddButton) {
            stage = (Stage) EmployeeAddButton.getScene().getWindow();
            myNewScene = FXMLLoader.load(MainController.class.getResource("AdminPanelListEmployeeView.fxml"));
        }
        Scene scene = new Scene(myNewScene);
        stage.setScene(scene);
        stage.setTitle("KrampTeckResto");
        stage.show();

    }

    @FXML
    private void ReturnMainMenu(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainMenu(stage);
    }
}
