package DbTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query extends QueryTools
{
    /**
     * Class that provides basic CRUD operations on db
     */

    public static boolean createTable(String tablename) throws SQLException {
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            String queryStr = createBuilder("table",tablename);
            try(PreparedStatement query = con.prepareStatement(queryStr))
            {
                return (!query.execute());
            }
            catch (SQLException e)
            {
                System.out.println("insert: "+ e.getMessage());
            }
        }
        finally {
            System.gc();
        }
        return  false;
    }


    public static boolean createDatabase(String dbname) throws SQLException {
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            String queryStr = createBuilder("db",dbname);
            try(PreparedStatement query = con.prepareStatement(queryStr))
            {
                return (!query.execute());
            }
            catch (SQLException e)
            {
                System.out.println("insert: "+ e.getMessage());
            }
        }
        finally {
            System.gc();
        }
        return  false;
    }

    //drops


    /**
     * 
     * @param queryStr
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<Object[]> select(String queryStr) throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        List<Object[]> resTab = new ArrayList<>();
        StringBuilder qr = new StringBuilder(queryStr);
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            try(PreparedStatement query = con.prepareStatement(qr.toString()))
            {
                ResultSet res = query.executeQuery();
                ResultSetMetaData meta = res.getMetaData();

                while (res.next())
                {
                    Object[] tmp = new Object[meta.getColumnCount()-1];
                    for (int i = 1; i < meta.getColumnCount() ; i++) {
                        tmp[i-1] = res.getObject(i);
                    }
                    resTab.add(tmp);
                }
                query.close();
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
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            try(PreparedStatement query = con.prepareStatement(queryStr))
            {
                return (!query.execute());
            }
            catch (SQLException e)
            {
                System.out.println("insert: "+ e.getMessage());
            }
        }
        finally {
            System.gc();
        }
        return false;
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
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            try(PreparedStatement query = con.prepareStatement(queryStr))
            {
                return (!query.execute());
            }
            catch (SQLException e)
            {
                System.out.println("insert: "+ e.getMessage());
            }
        }
        finally {
            System.gc();
        }
        return false;
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
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            try(PreparedStatement query = con.prepareStatement(queryStr))
            {
                return (!query.execute());
            }
            catch (SQLException e)
            {
                System.out.println("update: "+ e.getMessage());
            }
        }
        finally {
            System.gc();
        }
        return false;
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
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {

            try(PreparedStatement query = con.prepareStatement(queryStr))
            {
                return (!query.execute());
            }
            catch (SQLException e)
            {
                System.out.println("delete: "+ e.getMessage());
            }
        }
        finally {
            System.gc();
        }
        return  false;
    }










}
