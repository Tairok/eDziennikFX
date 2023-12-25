package DbTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query  {
    /*
     *  CRUD 
     * 
     */

    /**
     * 
     * @param queryStr
     * @param condition - Condition witch must be fullfilled by query (SQL WHERE clause) - Condition witch must be fullfilled by query (SQL WHERE clause)
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<Object[]> select(String queryStr,String condition) throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        List<Object[]> resTab = new ArrayList<>();
        StringBuilder qr = new StringBuilder(queryStr);
        if(condition != null) qr.append(" WHERE ").append(condition).append(";");
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
    public static boolean insert(String table,Object column,Object value) throws SQLException {

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
     * @param condition - Condition witch must be fullfilled by query (SQL WHERE clause)
     * @return
     * @throws SQLException
     */
    public static boolean update(String table,String[] columns,Object[] values,String condition) throws SQLException {
       String queryStr = updateBuilder(table, columns, values,condition);
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
     * @param values - Object table that contains data for query - String table that contains names of columns in table - String table that contains names of columns in table
     * @param values - Object table that contains data for query
     * @return
     * @throws SQLException
     */
    public static boolean update(String table,String[] columns,Object[] values) throws SQLException
    {
        String queryStr = (updateBuilder(table, columns, values,null));
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
     * @param condition - Condition witch must be fullfilled by query (SQL WHERE clause)
     * @return
     * @throws SQLException
     */
    public static boolean delete(String table,String condition) throws SQLException
    {
       String  queryStr = deleteBuilder(table,condition);
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

    /**
     * 
     * @param table - name of table
     * @return
     * @throws SQLException
     */
    public static boolean delete(String table) throws SQLException
    {
        String  queryStr = deleteBuilder(table,null);
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

    /**
     * auxiliary functions, for use inside class or inheritance
     */

    /**
     *
     * @param table - name of table - name of table
     * @return - returns the next id (auto-increment)
     * @throws SQLException
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
     *
     * @param table - name of table
     * @return
     * @throws SQLException
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

    //TODO - query builders

    /**
     *
     * @param table - name of table
     * @param values - Object table that contains data for query - String table that contains names of columns in table
     * @return
     * @throws SQLException
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
     *
     * @param table - name of table
     * @param column
     * @param value
     * @return
     * @throws SQLException
     */
    protected static String insertBuilder(String table,Object column,Object value) throws SQLException
    {
        StringBuilder qr= new StringBuilder("INSERT INTO school_db."+table+"("+getIdName(table)+","+column+") VALUES('"+getNextId(table)+"','"+value+"')");
        return qr.toString();
    }

    /**
     *
     * @param table - name of table
     * @param values - Object table that contains data for query - String table that contains names of columns in table
     * @param values
     * @param condition - Condition witch must be fullfilled by query (SQL WHERE clause)
     * @return
     */
    protected static String updateBuilder(String table,String[] columns,Object[] values,String condition)
    {
        if (columns.length != values.length) return "updateBuiler: Vales does not match columns";
        StringBuilder qr = new StringBuilder("UPDATE school_db." + table + " SET ");
        for (int i = 0; i < columns.length; i++) {
            if(i == columns.length-1) qr.append(columns[i]).append("=").append(values[i]);
            else qr.append(columns[i]).append("=").append(values[i]).append(",");
        }
        if(condition != null) qr.append(" WHERE ").append(condition).append(";");
        return qr.toString();
    }

    /**
     * 
     * @param table - name of table
     * @param condition - Condition witch must be fullfilled by query (SQL WHERE clause)
     * @return
     */
    protected static String deleteBuilder(String table,String condition)
    {
        StringBuilder qr = new StringBuilder("DELETE FROM school_db."+table);
        if(condition != null) qr.append(" WHERE "+condition+";");
        return qr.toString();
    }






}
