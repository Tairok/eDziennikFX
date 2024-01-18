package GuiTools;

import DbTools.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code User} class represents a user in the application.
 */
public class User {

    private static final Logger logger = LogManager.getLogger(User.class.getName());

    /**
     * The user ID.
     */
    public static int userID;

    /**
     * The user's first name.
     */
    public static String name;

    /**
     * The user's last name.
     */
    public static String surname;

    /**
     * The user's role.
     */
    public static String role;

    /**
     * The selected student's ID.
     */
    public static int selectedStudentID;

    /**
     * Checks if the provided login and password match a user in the database.
     *
     * @param login    The user's login.
     * @param password The user's password.
     * @return An array of user data if a match is found, or {@code null} otherwise.
     * @throws SQLException            If a SQL exception occurs.
     * @throws ClassNotFoundException If the required class is not found.
     */
    public static Object[] checkUser(String login, String password) throws SQLException, ClassNotFoundException {
        List<Object[]> loginData = Query.select(
                "SELECT * FROM users_tbl WHERE login='" + login + "' && password='" + password + "';"
        );

        if (loginData != null && !loginData.isEmpty()) {
            return loginData.get(0);
        } else {
            logger.warn("Login failed for user with login: {}", login);
            return null;
        }
    }
}
