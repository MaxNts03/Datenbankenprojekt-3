package Datenhaltungsschicht;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Verbindung {
    private static Connection con;
    private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    protected static Statement befehl;


    public static void connect(){
        try {
            con = DriverManager.getConnection(url, "system", "oracle"); //"C##FBPOOL167"
            befehl = con.createStatement();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() throws SQLException {
        con.close();
        System.out.println(con.isClosed());
    }

    public static Connection getConnection(){
        return con;
    }
}
