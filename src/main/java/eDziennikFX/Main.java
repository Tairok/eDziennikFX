package eDziennikFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
//custom
import DbTools.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();

        Config.clearDB();
        Config.fillDB();

    }

    public static void main(String[] args) {
        launch();
    }
}