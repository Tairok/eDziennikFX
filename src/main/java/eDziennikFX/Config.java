package eDziennikFX;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Configuration class for loading settings from a properties file.
 */
public class Config {

    private static final Logger logger = LogManager.getLogger(Config.class);

    /**
     * URL path to the database.
     */
    public static String DB_URL;

    /**
     * Username used to log into the database.
     */
    public static String DB_USER;

    /**
     * Password used to log into the database.
     */
    public static String DB_PASSWORD;

    /**
     * Port for the server application.
     */
    public static Integer SERVER_APP_PORT;

    /**
     * Loads configuration parameters from the properties file.
     */
    public static void loadConfig() {
        Properties properties = new Properties();
        String dir = System.getProperty("user.dir");
        Path file = Paths.get(dir).resolve("Config.txt");

        try (FileInputStream in = new FileInputStream(String.valueOf(file))) {
            properties.load(in);
            DB_URL = properties.getProperty("Database.url");
            DB_USER = properties.getProperty("Database.username");
            DB_PASSWORD = properties.getProperty("Database.password");
            SERVER_APP_PORT = Integer.parseInt(properties.getProperty("Server.app.port"));
        } catch (FileNotFoundException e) {
            logger.error("Config file not found.", e);
        } catch (IOException e) {
            logger.error("Error loading configuration.", e);
        }
    }
}
