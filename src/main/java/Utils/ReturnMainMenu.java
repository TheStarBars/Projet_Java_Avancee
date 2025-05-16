package Utils;

import com.example.projetjavaresto.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utility class providing static methods to navigate back to main views of the application.
 */
public class ReturnMainMenu {

    /**
     * Loads and displays the main user menu view in the given stage.
     *
     * @param currentStage the current window where the main view should be displayed
     * @throws IOException if the FXML file cannot be loaded
     */
    public static void MainMenu(Stage currentStage) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("MainView.fxml"));
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.setTitle("KrampTeckResto");
        currentStage.show();
    }

    /**
     * Loads and displays the admin panel view in the given stage.
     *
     * @param currentStage the current window where the admin view should be displayed
     * @throws IOException if the FXML file cannot be loaded
     */
    public static void AdminMenu(Stage currentStage) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("AdminPanelView.fxml"));
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.setTitle("KrampTeckResto");
        currentStage.show();
    }
}

