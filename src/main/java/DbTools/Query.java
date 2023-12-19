package DbTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query {

    public List<List<String>> queryTab = new ArrayList<>();
    public List<String> queryLabels = new ArrayList<>();


    public void printQuery()
    {
        System.out.println(queryLabels);
        for (int i = 0; i < this.queryTab.size(); i++) {

            List<String> tmp = this.queryTab.get(i);
            for (int j =0 ;j<tmp.size();j++) System.out.print(tmp.get(j)+" ");




        }
    }

    public Query(String queryStr) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        /**
         * temorary variables
         */
        List<String> rowTmp = new ArrayList<>();
        boolean labelRow = true;

        try( Connection con = DriverManager.getConnection(Config.DB_URL,Config.DB_USER,Config.DB_PASSWORD))
        {
            try(PreparedStatement query = con.prepareStatement(queryStr))
            {
                ResultSet res = query.executeQuery();
                ResultSetMetaData cols = res.getMetaData();
                int index =1,subIndex=1;
                for (int i=1;i<=cols.getColumnCount();i++) this.queryLabels.add(cols.getColumnLabel(i));

                while(res.next())
                {
                    for (int i = 1; i <= cols.getColumnCount() ; i++) {
                        rowTmp.add(res.getString(i));
                    }
                    this.queryTab.add(rowTmp);

                    index++;
                }
                rowTmp.removeAll();


                // end query and clean up
                //cols = null;
                //query.close();
                //con.close();



            }

        }
        catch(SQLException e) {
            e.printStackTrace();
            System.out.println("db: "+e.getMessage());
        }
        finally {
            //rowTmp = null;
            //System.gc();
        }
    }


}
