package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage clientStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("clientConnect.fxml"));
        clientStage.initStyle(StageStyle.UNDECORATED);
        clientStage.setTitle("Łączenie z serwerem");
        clientStage.setScene(new Scene(root, 600, 400));
        clientStage.show();
    }
    public static void main(String[] args) {
        launch(args);



    }
}
