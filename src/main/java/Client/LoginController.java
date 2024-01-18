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

import java.io.IOException;
import java.util.Arrays;

public class LoginController {
    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button loginButton;

    public void loginButtonOnAction(ActionEvent e) throws IOException {
        ConnectionHandler handler = ConnectionHandler.getInstance();
        String loginData = loginInput.getText() + "\n" + passwordInput.getText();
        Object[] userData = (Object[])(handler.sendMessage(loginData, PacketType.LOGIN_MSG)).getPayload();

        User.userID = (int)userData[0];
        User.name = (String)userData[1];
        User.surname = (String)userData[2];
        User.role = (String)userData[5];

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("selectStudent.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(root));



    }

    @FXML
    private Button disconnectButton;

    public void disconnectButtonOnAction(ActionEvent e)throws IOException{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("clientConnect.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        ConnectionHandler handler = ConnectionHandler.getInstance();
        handler.closeConnection();

    }
}
