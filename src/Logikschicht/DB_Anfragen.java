package Logikschicht;

import Datenhaltungsschicht.DB_Verbindung;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Anfragen {

    public static void getKurse() {
        String sqlString =
                "SELECT KursID, Kursname,COUNT(KundenID) AS Anzahl_Teilnehmer\n"+
                "FROM KundeKursBeziehung" +
                "GROUP BY KursID, Kursname" +
                "HAVING COUNT(KundenID) > 2;";
        {
            try {
                Statement stmt = DB_Verbindung.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sqlString);

                while (rs.next()) {
                    String KursID = rs.getString("KursID");
                    System.out.println("KursID: " +KursID);
                    String Kursname = rs.getString("Name");
                    System.out.println("Kursname: "+Kursname);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
