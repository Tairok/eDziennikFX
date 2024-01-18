package DbTools;

import DbTools.ClassTools.QueryTools;

import java.sql.*;
import java.util.List;

public class Query extends QueryTools
{
    /**
     * Class that provides basic CRUD operations on db
     */



    /**
     * 
     * @param queryStr
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<Object[]> select(String queryStr) throws SQLException, ClassNotFoundException
    {

        StringBuilder qr = new StringBuilder(queryStr);
        return QueryTools.sendResultQuery(String.valueOf(qr));
    }

    /**
     * 
     * @param table - name of table
     * @param values - Object table that contains data for query - String table that contains names of columns in table
     * @param values
     * @return
     * @throws SQLException
     */
    public static boolean insert(String table,String[] columns,Object[] values) throws SQLException
    {
        String queryStr = insertBuilder(table,columns,values);
        return QueryTools.sendBooleanQuery(queryStr);
    }

    /**
     * 
     * @param table - name of table
     * @param column
     * @param value
     * @return
     * @throws SQLException
     */
    public static boolean insert(String table,String column,Object value) throws SQLException {

        String queryStr = insertBuilder(table,column,value);
        return QueryTools.sendBooleanQuery(queryStr);
    }

    /**
     * 
     * @param table - name of table
     * @param values - Object table that contains data for query - String table that contains names of columns in table
     * @param values
     * @return
     * @throws SQLException
     */
    public static boolean update(String table,String[] columns,Object[] values) throws SQLException {
       String queryStr = updateBuilder(table, columns,values);
       return QueryTools.sendBooleanQuery(queryStr);
    }

    /**
     * 
     * @param table - name of table
     * @return
     * @throws SQLException
     */
    public static boolean delete(String table) throws SQLException
    {
        String  queryStr = deleteBuilder(table);
        return QueryTools.sendBooleanQuery(queryStr);
    }










}
