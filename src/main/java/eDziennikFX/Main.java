package eDziennikFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
//custom
import DbTools.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();


        //custom
        Query select = new Query("SELECT * FROM users_tbl");
        select.printQuery();
    }

    public static void main(String[] args) {
        launch();
    }
}