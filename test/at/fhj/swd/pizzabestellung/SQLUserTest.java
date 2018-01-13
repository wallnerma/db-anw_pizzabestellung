package at.fhj.swd.pizzabestellung;


import static org.junit.Assert.*;

import at.fhj.swd.spize.Transaction;

import javax.persistence.EntityManager;


@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class SQLUserTest {

    static final boolean verbose = true;

    // Adresse
    static final int id = 22;
    static final String strasse = "Max Musterweg";
    static final int hausnummer = 18;
    static final int plz = 8600;
    static final String ort = "Bruck/Mur";

    // Kunde
    static final String nickname = "terminator";
    static final String nachname = "Miller";
    static final String vorname = "John";
    static final String telnummer = "06601234567";


    static AdresseRepository adresseRepository;
    static KundeRepository kundeRepository;

    static Adresse adresse;
    static Kunde kunde;


    static boolean permissionDenied(Exception exc) {

        System.out.println(exc.getMessage());

        return exc.getMessage().contains("permission")
                && exc.getMessage().contains("denied");

    }

    public static void reset(EntityManager em) {
        Transaction.begin();
        kundeRepository.reset();
        adresseRepository.reset();
        Transaction.commit();
    }

    public static void create(EntityManager em) {

        Transaction.begin();
        adresse = adresseRepository.create(id, strasse, hausnummer, plz, ort);

        kunde = kundeRepository.create(nickname, adresse, nachname, vorname, telnummer);

        assertNotNull(adresse);
        assertNotNull(kunde);

        Transaction.commit();

        if (verbose) {
            System.out.println("Persisted " + adresse);
            System.out.println("Persisted " + kunde);
        }
    }
}