package Client;

import GuiTools.User;
import NetworkTools.Packet;
import NetworkTools.PacketType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Controller class for handling user login functionality.
 */
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button loginButton;

    /**
     * Handles the action event when the login button is clicked.
     *
     * @param e The ActionEvent triggered by the login button.
     * @throws IOException If an I/O exception occurs during the operation.
     */
    public void loginButtonOnAction(ActionEvent e) throws IOException {
        ConnectionHandler handler = ConnectionHandler.getInstance();
        String loginData = loginInput.getText() + "\n" + passwordInput.getText();
        Object[] userData = (Object[]) (handler.sendMessage(loginData, PacketType.LOGIN_MSG)).getPayload();

        User.userID = (int) userData[0];
        User.name = (String) userData[1];
        User.surname = (String) userData[2];
        User.role = (String) userData[5];

        // Load the next FXML scene after successful login
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("selectStudent.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(root));

        logger.info("User {} logged in. UserID: {}, Role: {}", User.name, User.userID, User.role);
    }

    @FXML
    private Button disconnectButton;

    /**
     * Handles the action event when the disconnect button is clicked.
     *
     * @param e The ActionEvent triggered by the disconnect button.
     * @throws IOException If an I/O exception occurs during the operation.
     */
    public void disconnectButtonOnAction(ActionEvent e) throws IOException {
        ConnectionHandler handler = ConnectionHandler.getInstance();

        // Load the client connection scene
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("clientConnect.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(root));

        // Close the connection to the server
        handler.closeConnection();

        logger.info("User {} disconnected.", User.name);
    }
}
