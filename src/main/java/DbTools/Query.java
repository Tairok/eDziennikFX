package DbTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query {


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
                System.out.println("db: "+ e.getMessage());
            }

        }
        finally {
            System.gc();
        }
        return resTab;
    }
    public static boolean insert(String table,String[] columns,Object[] values) throws SQLException
    {

        String queryStr = insertBuilder(table,columns,values);
        System.out.println(queryStr);
        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {

            try(PreparedStatement query = con.prepareStatement(queryStr))
            {
                return (!query.execute());
            }
            catch (SQLException e)
            {
                System.out.println("db: "+ e.getMessage());
            }
        }
        finally {
            System.gc();
        }
        return false;
    }

    //TODO - CRUD
    public static boolean update(String table,String[] columns,Object[] values,String condition)
    {
        System.out.println(updateBuiler(table, columns, values,condition));
        return false;
    }
    public static boolean update(String table,String[] columns,Object[] values)
    {
        System.out.println(updateBuiler(table, columns, values,null));
        return false;
    }

    public static boolean delete(String table,String condition)
    {
        System.out.println(deleteBuilder(table,condition));
        return  false;
    }

    public static boolean delete(String table)
    {
        System.out.println(deleteBuilder(table,null));
        return  false;
    }

    //TODO - raw functions
    private static Integer getNextId(String table) throws SQLException {
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
    private static String getIdName(String table) throws SQLException
    {
        String id = null;
        String tmpQuery="select * from school_db."+table+" limit 1;";


        try(Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            try(PreparedStatement query = con.prepareStatement(tmpQuery))
            {
                ResultSet res = query.executeQuery();
                ResultSetMetaData meta = res.getMetaData();
                if(res.next())  id = meta.getColumnLabel(1);

            }
            catch (SQLException e)
            {
                System.out.println("db: "+ e.getMessage());
            }
        }
        return id;
    }

    //TODO - query builders
    private static String insertBuilder(String table,String[] columns,Object[] values) throws SQLException
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
    private static String updateBuiler(String table,String[] columns,Object[] values,String condition)
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




    private static String deleteBuilder(String table,String condition)
    {
        StringBuilder qr = new StringBuilder("DELETE FROM school_db."+table);

        return qr.toString();
    }



}
