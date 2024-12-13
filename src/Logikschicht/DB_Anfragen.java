package Logikschicht;
import Datenhaltungsschicht.DB_Verbindung;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DB_Anfragen {

    public static void getKurse() {
        String sqlString =
                "SELECT KursID, Kursname,COUNT(KundenID) AS Anzahl_Teilnehmer\n" +
                        "FROM KundeKursBeziehung" +
                        "GROUP BY KursID, Kursname" +
                        "HAVING COUNT(KundenID) > 2;";
        {
            try {
                Statement stmt = DB_Verbindung.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sqlString);

                while (rs.next()) {
                    String KursID = rs.getString("KursID");
                    System.out.println("KursID: " + KursID);
                    String Kursname = rs.getString("Name");
                    System.out.println("Kursname: " + Kursname);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void getMitarbeitergehalt() {
        //„Welche Mitarbeiter arbeiten in welchem Fitnessstudio und wie hoch ist deren monatliches Gehalt“
        String sqlString = "SELECT Vorname, Nachname, Gehalt, Ortname\n" +
                "FROM Mitarbeiter, Ort\n" +
                "WHERE Ort.OrtID = Mitarbeiter.Arbeitsort\n" +
                "UNION \n" +
                "SELECT Vorname, Nachname, Gehalt, Ortname\n" +
                "FROM Management, Ort\n" +
                "WHERE Ort.Filialleiter = Management.ManagerID\n" +
                "GROUP BY Ort.Ortname";
        try {
            Statement stmts = DB_Verbindung.getConnection().createStatement();
            ResultSet rs2 = stmts.executeQuery(sqlString);

            while (rs2.next()) {
                String MaVorname = rs2.getString("Vorname");
                System.out.println(" Vorname:" + MaVorname);
                String MaNachname = rs2.getString("Nachname");
                System.out.println(" Nachname:" + MaNachname);
                String MaGehalt = rs2.getString("Gehalt");
                System.out.println(" Gehalt:" + MaGehalt);
                String MaOrt = rs2.getString("Ortname");
                System.out.println("Ort:" + MaOrt);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void getKurseUndKunden() {
        /*
        "Zeige mir alle Kurse von allen Standorten mit der jeweiligen Anzahl an Teilnehmern
        und dem zugehörigen Kursleiter und sortiere diese nach der Teilnehmeranzahl."
         */
        String sqlString = "SELECT o.Ortname AS Standort, k.Kursname AS Kurs, COUNT(ku.KundenID) AS Teilnehmeranzahl," +
                "ma.Vorname || ' ' || ma.Nachname AS Kursleiter" +
                "FROM KursKundeBeziehung kkb" +
                "JOIN Ort o ON kkb.Teilnehmende_Orte = o.OrtID" +
                "JOIN Kurs k ON kkb.KursID = k.KursID" +
                "JOIN Kunde ku ON kkb.Teilnehmer = ku.KundenID" +
                "JOIN Mitarbeiter ma ON kkb.Kursleiter = ma.MitarbeiterID" +
                //--WHERE o.Ortname = 'Gym am SEE'
                "GROUP BY o.Ortname, k.Kursname, ma.Vorname, ma.Nachname" +
                "ORDER BY Teilnehmeranzahl DESC, k.Kursname;";
        {
            try {
                Statement stmt = DB_Verbindung.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sqlString);

                while (rs.next()) {
                    String Standort = rs.getString("Standort");
                    System.out.println("Standort: " + Standort);
                    String Kurs = rs.getString("Kurs");
                    System.out.println("Kursname: " + Kurs);
                    Integer Teilnehmer = rs.getInt("Teilnehmeranzahl");
                    System.out.println("Teilnehmeranzahl: " + Teilnehmer);
                    String Kursleiter = rs.getString("Kursleiter");
                    System.out.println("Kursleiter: " + Kursleiter);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
//„Liste alle Geräte eines Studios auf und gruppiere sie nach dem Typ.
//Gebe zusätzlich den verantwortlichen Manager an“

    public static void Geraetetypen() {
        String sqlString = "SELECT i.Typ AS Gerätetyp," +
                "i.Gerätename AS Gerät," +
                "m.Vorname || ' ' || m.Nachname AS Verantwortlicher" +
                "FROM Inventar i" +
                "JOIN Management m ON i.Verwalter = m.ManagerID" +
                "ORDER BY i.Typ, i.Gerätename";

        try {
            Statement stmt = DB_Verbindung.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sqlString);


            while (rs.next()) {
                String typ = rs.getString("Gerätetyp");
                System.out.println("Gerteätyp:" + typ);
                String name = rs.getString("Gerät");
                System.out.println("Gerätename:" + name);
                String verantw = rs.getString("Verantwortlicher");
                System.out.println("Verantwortlicher" + verantw);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getBenefitsMenu() {
        Scanner sc = new Scanner(System.in);
        int counter = 3;
        System.out.println("Zu welchem Abo möchten sie alle Benefits bekommen?");
        String sqlString = "SELECT AboID,Aboname" +
                "FROM Abo";
        try {
            Statement stmt = DB_Verbindung.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sqlString);
            while (rs.next()) {
                int AboId = rs.getInt("AboID");
                System.out.println("AboID: " + AboId);
                String Aboname = rs.getString("Aboname");
                System.out.println("Aboname: " + Aboname);
                counter++;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        try {
            System.out.println("Bitte geben Sie eine Zahl zwichen 1 und " + counter + " ein");
            int eingabe = sc.nextInt();
            if (eingabe < 1 || eingabe > counter) {
                System.out.println("Die Zahl ist nicht erlaubten Bereich. Bitte geben sie Eine Zahl zwischen 1 und " + counter + " ein");
                getBenefitsMenu();
            } else {
                String Benefit = getAbonameForPrSt(eingabe);
                getBenefits(Benefit);
            }
        } catch (InputMismatchException e) {
            System.out.println("Der eingegebene Wert ist keine Zahl. Bitte ein gültige Zahl eingeben");
            sc.next();
        }
    }

    public static void getBenefits(String Abo){
        String sqlString = "SELECT DISTINCT\n" +
                "    LEVEL AS Hierarchie_Ebene,\n" +
                "    AboID,\n" +
                "    Aboname,\n" +
                "    FeatureID,\n" +
                "    Beschreibung\n" +
                "FROM \n" +
                "    Abo \n" +
                "JOIN Feature  USING(AboID)\n" +
                "START WITH \n" +
                "    Aboname = ?\n" +
                "CONNECT BY PRIOR \n" +
                "    UebergeordnetesAboID = AboID\n" +
                "Order by  \n" +
                " Hierarchie_Ebene ASC,\n" +
                " AboID ASC";
        try{
            PreparedStatement prst = DB_Verbindung.getConnection().prepareStatement(sqlString);
            prst.setString(1, Abo +"");
            ResultSet prs = prst.executeQuery();
            while (prs.next()){
                String hierarchie = prs.getString("Hierarchie_Ebene");
                System.out.println("Ebene"+ hierarchie);
                String aboId = prs.getString("AboID");
                System.out.println("AboId"+ aboId);
                String aboname = prs.getString("Aboname");
                System.out.println("Aboname:"+aboname);
                String featureid = prs.getString("FeatureID");
                System.out.println("FeatureID:"+ featureid);
                String bes = prs.getString("Beschreibung");
                System.out.println("Beschreibung:"+bes);
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public static String getAbonameForPrSt(int Nutzereingabe) {
        String sqlString = "SELECT Aboname FROM Abo WHERE AboID = ?";
        String erg ="";
        try {
            PreparedStatement prstmt = DB_Verbindung.getConnection().prepareStatement(sqlString);
            prstmt.setString(1, Nutzereingabe + "");
            ResultSet prs = prstmt.executeQuery();
            while (prs.next()) {
                String Aboname = prs.getString("Aboname");
                erg = Aboname;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return erg;
    }
}
