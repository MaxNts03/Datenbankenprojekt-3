import Datenhaltungsschicht.DB_Verbindung;
import Präsentationsschicht.Konsolenmenu;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        DB_Verbindung.connect();
        Konsolenmenu.printMenu();




        /*
        DB_Verbindung.connect();

        try {
            DB_Verbindung.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        */
    }
}
