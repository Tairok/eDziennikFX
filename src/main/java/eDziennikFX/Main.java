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


        //custom
        /*
        List<Object[]> test =  Query.select("SELECT * FROM school_db.users_tbl;");
        for(Object[] ob : test)
        {
            for(Object o : ob) System.out.print(o+" ");
            System.out.println();
        }*/

        String[] cols={"first_name","last_name","role","login","password"};
        Object[] values ={"test","test","test","test","test"};
        //System.out.println(Query.insert("users_tbl",cols,values));
        //Query.update("users_tbl",cols,values,"id_user=3");
        Query.delete("users_tbl");
    }

    public static void main(String[] args) {
        launch();
    }
}