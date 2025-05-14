package Utils;

import com.example.projetjavaresto.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReturnMainMenu {
    public static void MainMenu(Stage currentStage) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("MainView.fxml"));
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.setTitle("KrampTeckResto");
        currentStage.show();
    }
}
