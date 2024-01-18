package DbTools.ClassTools;

import eDziennikFX.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class QueryTools {
    /**
     * The QueryTools class provides utility methods for building and executing SQL queries.
     * It includes methods for creating INSERT, UPDATE, and DELETE queries, as well as auxiliary functions
     * for printing query results and obtaining information about database tables.
     */

    /**
     * Builds and returns an SQL INSERT query for the specified table, columns, and values.
     *
     * @param table   The name of the table.
     * @param columns An array of column names.
     * @param values  An array of corresponding values for the columns.
     * @return The generated SQL INSERT query.
     * @throws SQLException If a database access error occurs.
     */
    protected static String insertBuilder(String table,String[] columns,Object[] values) throws SQLException
    {


        StringBuilder qr= new StringBuilder("INSERT INTO school_db."+table+"(");
        for (int i = 0; i < columns.length ; i++) {
            if(i==0) qr.append(getIdName(table)).append(",").append(columns[i]).append(",");
            else if (i == columns.length -1) qr.append(columns[i]).append(")");
            else qr.append(columns[i]).append(",");
        }
        for (int i = 0; i < values.length; i++) {
            if(i==0) qr.append("VALUES(").append(getNextId(table)).append(",'").append(values[i]).append("',");
            else if (i == values.length -1) qr.append("'").append(values[i]).append("');");
            else  qr.append("'").append(values[i]).append("',");
        }
        return qr.toString();
    }

    /**
     * Builds and returns an SQL INSERT query for the specified table, column, and value.
     *
     * @param table  The name of the table.
     * @param column The name of the column.
     * @param value  The value to be inserted.
     * @return The generated SQL INSERT query.
     * @throws SQLException If a database access error occurs.
     */
    protected static String insertBuilder(String table,String column,Object value) throws SQLException
    {
        StringBuilder qr= new StringBuilder("INSERT INTO school_db."+table+"("+getIdName(table)+","+column+") VALUES('"+getNextId(table)+"','"+value+"')");
        return qr.toString();
    }

    /**
     * Builds and returns an SQL UPDATE query for the specified table, columns, and values.
     *
     * @param table   The name of the table.
     * @param columns An array of column names.
     * @param values  An array of corresponding values for the columns.
     * @return The generated SQL UPDATE query.
     */
    protected static String updateBuilder(String table,String[] columns,Object[] values)
    {
        if (columns.length != values.length) return "updateBuiler: Vales does not match columns";
        StringBuilder qr = new StringBuilder("UPDATE school_db." + table + " SET ");
        for (int i = 0; i < columns.length; i++) {
            if(i == columns.length-1) qr.append(columns[i]).append("=").append(values[i]);
            else qr.append(columns[i]).append("=").append(values[i]).append(",");
        }
        qr.append(";");
        return qr.toString();
    }

    /**
     * Builds and returns an SQL DELETE query for the specified table.
     *
     * @param table The name of the table.
     * @return The generated SQL DELETE query.
     */
    protected static String deleteBuilder(String table)
    {
        StringBuilder qr = new StringBuilder("DELETE FROM school_db."+table);
        qr.append(";");
        return qr.toString();
    }




    /**
     * Prints the result of a SELECT query, provided as a List of Object arrays.
     *
     * @param resultTab The List containing Object arrays representing the query result.
     */
    public static void printSelect(List<Object[]> resultTab)
    {
        for(Object[] objects : resultTab)
        {
            for(Object object : objects) System.out.print(object +" ");
            System.out.println();
        }
    }


    /**
     * Retrieves the next auto-incremented ID for the specified table.
     *
     * @param table The name of the table.
     * @return The next auto-incremented ID.
     * @throws SQLException If a database access error occurs.
     */
    protected static Integer getNextId(String table) throws SQLException
    {
        String tmpQuery="select count(*) from school_db."+table;
        int lastId=0;
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            try(PreparedStatement query = con.prepareStatement(tmpQuery))
            {
                ResultSet res = query.executeQuery();
                if(res == null)
                {
                    System.out.println("getNextId(): null");
                    return -1;
                }
                if(res.next())  lastId = res.getInt(1);

            }
            catch (SQLException e)
            {
                System.out.println("db: "+ e.getMessage());
            }
        }
        return lastId+1;
    }

    /**
     * Retrieves the name of the primary key column (ID column) for the specified table.
     *
     * @param table The name of the table.
     * @return The name of the primary key column.
     * @throws SQLException If a database access error occurs.
     */
    protected static String getIdName(String table) throws SQLException
    {
        String id = null;
        String tmpQuery="select * from school_db."+table+" limit 1;";
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            try(PreparedStatement query = con.prepareStatement(tmpQuery))
            {
                ResultSet res = query.executeQuery();
                ResultSetMetaData meta = res.getMetaData();
                id = meta.getColumnLabel(1);
            }
            catch (SQLException e)
            {
                System.out.println("db: "+ e.getMessage());
            }
        }
        return id;
    }

    /**
     * Executes a boolean query and returns the result.
     *
     * @param query The boolean query to be executed.
     * @return true if the query is successful, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    protected static boolean sendBooleanQuery(String query) throws SQLException
    {
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {

            try(PreparedStatement qr = con.prepareStatement(query))
            {
                return (!qr.execute());
            }
            catch (SQLException e)
            {
                System.out.println("sendBooleanQuery: "+ e.getMessage());
            }
        }
        finally {
            System.gc();
        }

        return false;
    }

    /**
     * Executes a query and returns the result as a List of Object arrays.
     *
     * @param query The query to be executed.
     * @return A List of Object arrays representing the query result.
     * @throws SQLException              If a database access error occurs.
     * @throws ClassNotFoundException    If the class definition for the driver is not found.
     */
    protected static List<Object[]>  sendResultQuery(String query) throws SQLException, ClassNotFoundException {

        List<Object[]> resTab = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            try(PreparedStatement qr = con.prepareStatement(query))
            {
                ResultSet res = qr.executeQuery();
                ResultSetMetaData meta = res.getMetaData();

                while (res.next())
                {
                    Object[] tmp = new Object[meta.getColumnCount()];
                    for (int i = 1; i <= meta.getColumnCount() ; i++) {
                        tmp[i-1] = res.getObject(i);
                    }
                    resTab.add(tmp);
                }
                qr.close();
                res.close();
            }
            catch (SQLException e)
            {
                System.out.println("select: "+ e.getMessage());
            }

        }
        finally {
            System.gc();
        }

        //check if resultTab is not null
        if(resTab.isEmpty()) return null;
        return resTab;
    }
}
