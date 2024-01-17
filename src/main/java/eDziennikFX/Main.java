package eDziennikFX;

import Gui.GuiForm;
import javafx.application.Application;
import javafx.stage.Stage;

import DbTools.*;

import java.sql.SQLException;

public class Main extends Application {


    public static void main(String[] args) throws SQLException {


        //Server server = new Server(7245);
        try
        {
            Config.clearDB(); //clear whole db!, drop all tables !!!!

            Config.fillDB(); //fill db with sample data
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        //create gui
        GuiForm form = new GuiForm(stage);

    }
}

