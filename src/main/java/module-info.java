module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires org.apache.logging.log4j.core;


    opens eDziennikFX to javafx.fxml;
    exports eDziennikFX;
    exports DbTools;
    opens DbTools to javafx.fxml;
    exports DbTools.ClassTools;
    opens DbTools.ClassTools to javafx.fxml;
    opens Client to javafx.fxml;
    exports Client;

}