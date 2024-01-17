package GuiTools;
import DbTools.Query;

import java.sql.SQLException;
import java.util.List;

public class User {
    public static boolean checkUser(String login,String password) throws SQLException, ClassNotFoundException {
        List<Object[]> LoginTab = Query.select(
                "SELECT login FROM users_tbl WHERE login='"+login+"' && password='"+password+"';"
        );
        System.out.println(LoginTab);
        return LoginTab != null;
    }



}
