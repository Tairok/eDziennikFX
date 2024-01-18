package DbTools;

import DbTools.ClassTools.QueryTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code Query} class provides basic CRUD operations on a database using JDBC.
 */
public class Query extends QueryTools {

    private static final Logger logger = LogManager.getLogger(Query.class.getName());

    /**
     * Executes a SELECT query and returns the result set.
     *
     * @param queryStr The SELECT query string.
     * @return A list of Object arrays representing the query results.
     * @throws SQLException            If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
     */
    public static List<Object[]> select(String queryStr) throws SQLException, ClassNotFoundException {
        StringBuilder qr = new StringBuilder(queryStr);
        return QueryTools.sendResultQuery(String.valueOf(qr));
    }

    /**
     * Inserts data into the specified table.
     *
     * @param table   The name of the table.
     * @param columns An array of column names.
     * @param values  An array of values to be inserted.
     * @return True if the insertion is successful, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public static boolean insert(String table, String[] columns, Object[] values) throws SQLException {
        String queryStr = insertBuilder(table, columns, values);
        return QueryTools.sendBooleanQuery(queryStr);
    }

    /**
     * Inserts a single value into the specified column of the table.
     *
     * @param table  The name of the table.
     * @param column The name of the column.
     * @param value  The value to be inserted.
     * @return True if the insertion is successful, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public static boolean insert(String table, String column, Object value) throws SQLException {
        String queryStr = insertBuilder(table, column, value);
        return QueryTools.sendBooleanQuery(queryStr);
    }

    /**
     * Updates data in the specified table.
     *
     * @param table   The name of the table.
     * @param columns An array of column names.
     * @param values  An array of new values.
     * @return True if the update is successful, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public static boolean update(String table, String[] columns, Object[] values) throws SQLException {
        String queryStr = updateBuilder(table, columns, values);
        return QueryTools.sendBooleanQuery(queryStr);
    }

    /**
     * Deletes all records from the specified table.
     *
     * @param table The name of the table.
     * @return True if the deletion is successful, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public static boolean delete(String table) throws SQLException {
        String queryStr = deleteBuilder(table);
        return QueryTools.sendBooleanQuery(queryStr);
    }
}
