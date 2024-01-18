package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The main class that serves as the entry point for the client application.
 */
public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * The main entry point for the client application.
     *
     * @param clientStage The primary stage for the application.
     */
    @Override
    public void start(Stage clientStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("clientConnect.fxml"));
            clientStage.initStyle(StageStyle.UNDECORATED);
            clientStage.setTitle("Łączenie z serwerem");
            clientStage.setScene(new Scene(root, 600, 400));
            clientStage.show();

            logger.info("Client application started.");
        } catch (IOException e) {
            logger.error("Error loading FXML: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * The main method that launches the client application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);

        logger.info("Client application exiting.");
    }
}
