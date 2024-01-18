package DbTools.ClassTools;

import DbTools.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO in development
public class QueryTools {
    /**
     * Query builders - used to build queries from strings
     */

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
    protected static String insertBuilder(String table,String column,Object value) throws SQLException
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
     *
     * @param table - name of table
     * @param condition - Condition witch must be fullfilled by query (SQL WHERE clause)
     * @return
     */
    protected static String deleteBuilder(String table)
    {
        StringBuilder qr = new StringBuilder("DELETE FROM school_db."+table);
        qr.append(";");
        return qr.toString();
    }



    /**
     * Optional functions for diagnosis
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

    //TODO do generic function
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
