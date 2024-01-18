package DbTools;
import java.sql.SQLException;

public class Config extends Query{
    /**
     * URL path to database
     */
    public static final String DB_URL = "jdbc:mysql://localhost:3306/school_db";


    /**
     * Username, used to log into database
     */
    public static final String DB_USER = "root";

    /**
     * Password, used to log into database
     */
    public static final String DB_PASSWORD = "zaq1@WSX";

    //TODO create whole db
    public static void createDB()
    {

    }

    /**
     * create structure of database
     *
     */

    /**
     * Fill whole database with sample data
     * @throws SQLException
     */
    public static void fillDB() throws SQLException
    {

        //Insert sample data into classes_tbl
        insert("classes_tbl","class_name","Class A");
        insert("classes_tbl","class_name","Class B");
        insert("classes_tbl","class_name","Class C");

        //Insert sample data into subjects_tbl
        insert("subjects_tbl","subject_name","Mathematics");
        insert("subjects_tbl","subject_name","Science");
        insert("subjects_tbl","subject_name","English");


        //Insert sample data into users_tbl
        String[] columns = {"first_name", "last_name", "login", "password", "role", "fk_id_class_user_tbl"};
        Object[] values ={"John", "Doe", "john_doe", "password123", "student", 1};
        insert("users_tbl",columns,values);

        values= new Object[]{"Jane", "Smith", "jane_smith", "securepass", "teacher", 2};
        insert("users_tbl",columns,values);

        values= new Object[]{"Admin", "Adminson", "admin", "admin", "admin", 1};
        insert("users_tbl",columns,values);

        values= new Object[]{"Admin2", "Adminson2", "a", "a", "admin", 1};
        insert("users_tbl",columns,values);

        values= new Object[]{"Teacher", "Teacherson", "teacher", "teacher", "teacher", 2};
        insert("users_tbl",columns,values);

        values= new Object[]{"Teacher2", "Teacherson2", "t", "t", "teacher", 2};
        insert("users_tbl",columns,values);

        values = new Object[]{"Student", "Studentson", "student", "student", "student", 1};
        insert("users_tbl",columns,values);

        values = new Object[]{"Studenta2", "Studentdottir2", "s", "s", "student", 1};
        insert("users_tbl",columns,values);

        //Insert sample data into grades_tbl
        columns = new String[]{"grade", "grade_Date", "grade_title", "fk_id_user_grades_tbl", "fk_id_subject_grades_tbl"};
        values = new Object[]{3, "2023-12-23", "Math Test", 1, 1};
        insert("grades_tbl",columns,values);
        
        values = new Object[]{4, "2023-12-22", "Science Quiz", 1, 2};
        insert("grades_tbl",columns,values);
        
        values = new Object[]{1, "2023-12-21", "English Assignment", 2, 3};
        insert("grades_tbl",columns,values);

    }

    /**
     * Clear whole database
     * @throws SQLException
     */
    public static void clearDB() throws SQLException
    {
        delete("grades_tbl");
        delete("subjects_tbl");
        delete("users_tbl");
        delete("classes_tbl");
    }
}
