package equipmenttrackingsystem.open;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author
 */
public class OpenForm {

    /**
     * open service form
     * @param screen
     * @param title
     */
    public  void openServiceListScreen(String screen,String title) {
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(screen));
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {

        }

    }

}
