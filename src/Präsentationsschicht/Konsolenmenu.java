package Präsentationsschicht;

import Datenhaltungsschicht.DB_Verbindung;
import Logikschicht.DB_Anfragen;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Konsolenmenu {
    public static void printMenu() {
        Scanner sc = new Scanner(System.in);
        boolean Hauptprogramm = true;
        boolean anfragen = true;
        while (Hauptprogramm) {
            System.out.println("Bitte wählen sie eine der folgenden Optionen:");
            System.out.println("1. Abfragen ausführen");
            System.out.println("2. Tupel in rekursive Beziehung einfügen");
            System.out.println("3. rekursive Tiefensuche durchführen");
            System.out.println("4. Programm beenden");
            System.out.println("Geben Sie ihre Auswahl ein:");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    while (anfragen) {
                        try{
                            printPossibleStatements();
                            int eingabe = sc.nextInt();
                            if (eingabe <= 0 || eingabe > 5) {
                                System.out.println("Falsche Eingabe, bitte geben Sie eine zwischen 1 und 5 ein");
                                System.out.println("");
                            } else if (eingabe == 1) {
                                DB_Anfragen.getKurse();
                                anfragen = false;
                            }  else if (eingabe == 2) {
                                DB_Anfragen.getMitarbeitergehalt();
                                anfragen = false;
                            } else if (eingabe == 3) {
                                DB_Anfragen.getKurseUndKunden();
                                anfragen = false;
                            } else if (eingabe == 4) {

                            } else if (eingabe == 5){
                                DB_Anfragen.getBenefitsMenu();
                            }


                            else {
                                System.out.println("Probe ob es funktioniert");
                            }
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Der eingegebene Wert ist keine Zahl");
                            sc.next();

                        }
                    }
                break;
                case 2:
                    //Tupel in rekursive Beziehung einfügen
                    System.out.println("Das ist case 2");
                    break;
                case 3:
                    System.out.println("Das ist case 3");
                    break;
                case 4:
                    System.out.println("Das Programm wird beendet");
                    try {
                        sc.close();
                        DB_Verbindung.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Hauptprogramm = false;
                    break;
                default:
                    System.out.println("Falsche eingabe!");
            }

        }
    }

    public static void printPossibleStatements() {
        System.out.println("[1]. Zeige alle Kurse an, in der mehr als 10 Kunden sind");
        System.out.println("[2]. Welche Mitarbeiter arbeiten in welchem Fitnessstudio und wie hoch ist deren monatliches Gehalt");
        System.out.println("[3]. Zeige mir alle Kurse von allen Standorten mit der jeweiligen Anzahl an Teilnehmern\n" +
                "    und dem zugehörigen Kursleiter und sortiere diese nach der Teilnehmeranzahl.");
        System.out.println("[4]. Liste alle Geräte eines Studios auf und gruppiere sie nach dem Typ. \n" +
                "   Gebe zusätzlich den verantwortlichen Manager an");
        System.out.println("[5]. Liste alle Geräte eines Studios auf und gruppiere sie nach dem Typ. Gebe zusätzlich den verantwortlichen Manager an");
        System.out.println("[6]. Exit");
    }

}
