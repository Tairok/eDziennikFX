package Gui;

import Client.ConnectionHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
/*
public class InitConnection {
    private TextField ipAddressInput;
    private TextField portInput;
    private Stage stage;
    public InitConnection(Stage s){
        stage = s;
        stage.setTitle("Połaczenie z serwerem");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label ipAddressLabel = new Label("Adres IP:");
        GridPane.setConstraints(ipAddressLabel, 0,0);

        ipAddressInput = new TextField();
        GridPane.setConstraints(ipAddressInput,1, 0);

        Label portLabel = new Label("Port:");
        GridPane.setConstraints(portLabel, 0,1);

        portInput = new TextField();
        GridPane.setConstraints(portInput,1, 1);

        Button connectButton = new Button("Połącz");
        GridPane.setConstraints(connectButton, 1,2);

        connectButton.setOnAction(this::login);


        grid.getChildren().addAll(ipAddressLabel, ipAddressInput, portLabel, portInput, connectButton);


        Scene scene = new Scene(grid, 300, 150);
        stage.setScene(scene);
        stage.show();
        //stage.hide();
    }

    public void login(ActionEvent event){
        if(ipAddressInput.getText().trim().isEmpty()){
            System.out.println("Wrong ip address");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad!");
            alert.setHeaderText("Podaj poprawny adres IP");
            alert.show();
        }
        if(portInput.getText().trim().isEmpty()){
            System.out.println("Wrong port number");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad!");
            alert.setHeaderText("Podaj poprawny port");
            alert.show();
        }
        else{
            ConnectionHandler handler = new ConnectionHandler(ipAddressInput.getText(), Integer.parseInt(portInput.getText()));
            stage.hide();
        }

    }
}
 */