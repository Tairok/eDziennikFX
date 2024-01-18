package eDziennikFX;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.*;

import java.sql.SQLException;

/**
 * The Main class is the entry point for the eDziennikFX application.
 */
public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    /**
     * The main method of the application.
     *
     * @param args The command-line arguments.
     * @throws SQLException If a SQL exception occurs.
     */
    public static void main(String[] args) throws SQLException {
        Server server = new Server(7245);

        launch(args);
    }

    /**
     * The start method is called when the Application is launched.
     *
     * @param stage The primary stage for the application.
     * @throws Exception If an exception occurs during application startup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        logger.info("eDziennikFX application started successfully.");
    }
}
