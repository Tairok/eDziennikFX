package Client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Controller class for handling the connection-related functionality of the client application.
 */
public class ConnectController {

    private static final Logger logger = LogManager.getLogger(ConnectController.class);

    @FXML
    private TextField addressInput;

    @FXML
    private TextField portInput;

    @FXML
    private Button connectButton;

    /**
     * Handles the action event when the connect button is clicked.
     *
     * @param e The ActionEvent triggered by the connect button.
     * @throws IOException If an I/O exception occurs during the operation.
     */
    public void connectButtonOnAction(ActionEvent e) throws IOException {
        logger.info("Connect button clicked.");

        // Create a ConnectionHandler instance
        ConnectionHandler handler = ConnectionHandler.getInstance(addressInput.getText(), Integer.parseInt(portInput.getText()));

        // Load the client login FXML
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("clientLogin.fxml"));
        Stage stage = (Stage) connectButton.getScene().getWindow();
        stage.setScene(new Scene(root));

        logger.info("Connection established to {}:{}", addressInput.getText(), portInput.getText());
    }

    @FXML
    private Button quitButton;

    /**
     * Handles the action event when the quit button is clicked.
     *
     * @param e The ActionEvent triggered by the quit button.
     */
    public void quitButtonOnAction(ActionEvent e){
        logger.info("Quit button clicked.");

        // Close the application window
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
}
