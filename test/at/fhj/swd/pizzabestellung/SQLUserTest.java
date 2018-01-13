package at.fhj.swd.pizzabestellung;


import java.util.List;

import static org.junit.Assert.*;
import        org.junit.BeforeClass;
import        org.junit.AfterClass;
import        org.junit.Test;

import at.fhj.swd.spize.Transaction;
import javax.persistence.EntityManager;

import at.fhj.swd.persistence.Persistence;

@org.junit.FixMethodOrder( org.junit.runners.MethodSorters.NAME_ASCENDING)
public class SQLUserTest{

    static final boolean verbose = true;

    // Adresse
    static final int id = 22;
    static final String strasse = "Max Musterweg";
    static final int hausnummer = 18;
    static final int plz = 8600;
    static final String ort = "Bruck/Mur";

    // Kunde
    static  final String nickname = "terminator";
    static  final String nachname = "Miller";
    static  final String vorname  = "John";
    static  final String telnummer = "06601234567";

    // Bestellungen
    static final int id1 = 23;
    static final int id2 = 24;
    static final String status1 = "in Arbeit";
    static final String status2 = "aufgenommen";

    // Pizza 1
    static private int idPizza1 = 102;
    static private String name1 = "Salami";
    static private String groesse1 = "L";
    static private float einzelpreis1 = 27.20f;

    // Pizza 2
    static private int idPizza2 = 145;
    static private String name2 = "Tonno";
    static private String groesse2 = "L";
    static private float einzelpreis2 = 20.25f;

    static AdresseRepository adresseRepository;
    static KundeRepository kundeRepository;
    static BestellungRepository bestellungRepository;
    static PizzaRepository pizzaRepository;

    static Adresse adresse;
    static Kunde kunde;
    static Bestellung bestellung1;
    static Bestellung bestellung2;
    static Pizza pizza1;
    static Pizza pizza2;


    static boolean permissionDenied (Exception exc)
    {

        System.out.println (exc.getMessage());

        return    exc.getMessage().contains ("permission")
                && exc.getMessage().contains ("denied");

    }

    public static void reset (EntityManager em)
    {
        Transaction.begin();
        kundeRepository.reset();
        adresseRepository.reset();
        Transaction.commit();
    }

}