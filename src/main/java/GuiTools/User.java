package GuiTools;
import DbTools.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class User {
    public static int userID;
    public static String name;
    public static String surname;
    public static String role;
    public static int selectedStudentID;



    public static Object[] checkUser(String login,String password) throws SQLException, ClassNotFoundException {
        List<Object[]> LoginTab = Query.select(
                "SELECT * FROM users_tbl WHERE login='"+login+"' && password='"+password+"';"
        );
        if(LoginTab != null){
            return LoginTab.get(0);
        }
        else{
            return null;
        }

    }



}
