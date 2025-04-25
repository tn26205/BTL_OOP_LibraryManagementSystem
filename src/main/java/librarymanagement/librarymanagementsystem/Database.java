package librarymanagement.librarymanagementsystem;


import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    static Connection connect  = null;
    public static Connection connectDb() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/library", "root", "");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connect;
    }
}
