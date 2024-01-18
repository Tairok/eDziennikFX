package Client;

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

import java.io.IOException;

public class ConnectController {
    @FXML
    private TextField addressInput;

    @FXML
    private TextField portInput;

    @FXML
    private Button connectButton;

    public void connectButtonOnAction(ActionEvent e) throws IOException {
        ConnectionHandler handler = ConnectionHandler.getInstance(addressInput.getText(), Integer.parseInt(portInput.getText()));

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("clientLogin.fxml"));
        Stage stage = (Stage) connectButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private Button quitButton;

    public void quitButtonOnAction(ActionEvent e){
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

}
