package at.fhj.swd.pizzabestellung;


import java.util.List;

import static org.junit.Assert.*;
import        org.junit.BeforeClass;
import        org.junit.AfterClass;
import        org.junit.Test;

import at.fhj.swd.spize.Transaction;

import at.fhj.swd.persistence.Persistence;

@org.junit.FixMethodOrder( org.junit.runners.MethodSorters.NAME_ASCENDING)
public class PizzaBestellungTestRepo {

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

    @BeforeClass
    public static void setup() {
        adresseRepository = new AdresseRepository();
        kundeRepository = new KundeRepository();
        bestellungRepository = new BestellungRepository();
        pizzaRepository = new PizzaRepository();

        Transaction.begin();
        pizzaRepository.reset();
        bestellungRepository.reset();
        kundeRepository.reset();
        adresseRepository.reset();
        Transaction.commit();
    }

    @AfterClass
    public static void teardown() { Persistence.close();}

    @Test public void a_create () {
        Transaction.begin();

        adresse = adresseRepository.create(id,strasse,hausnummer,plz,ort);

        kunde = kundeRepository.create(nickname, adresse, nachname,vorname,telnummer);

        bestellung1 = bestellungRepository.create(id1, status1);
        bestellung2 = bestellungRepository.create(id2, status2);

        pizza1 = pizzaRepository.create(idPizza1, name1, groesse1, einzelpreis1);
        pizza2 = pizzaRepository.create(idPizza2, name2, groesse2, einzelpreis2);

        assertNotNull(adresse);
        assertNotNull(kunde);
        assertNotNull(bestellung1);
        assertNotNull(bestellung2);
        assertNotNull(pizza1);
        assertNotNull(pizza2);

        Transaction.commit();

        if(verbose){
            System.out.println("Persisted " + adresse);
            System.out.println("Persisted " + kunde);
            System.out.println("Persisted " + bestellung1);
            System.out.println("Persisted " + bestellung2);
            System.out.println("Persisted " + pizza1);
            System.out.println("Persisted " + pizza2);
        }
    }

    @Test public void b_join()
    {
        Transaction.begin();

        bestellung1.set(kunde);
        bestellung2.set(kunde);

        pizza1.add(bestellung1);
        pizza2.add(bestellung2);

        Transaction.commit();
    }

    @Test public void c_verify ()
    {
        if (verbose) bestellungRepository.printAll("id");
        if (verbose) pizzaRepository.printAll("id");

        // Adressen ----------------------------------------
        List<Adresse> adresses = adresseRepository.findAll("id");
        assertEquals(1,adresses.size());

        adresse = adresseRepository.find(id);
        assertNotNull(adresse);

        assertEquals(adresse, adresses.get(0));

        assertEquals (kunde, adresse.getKunde() );

        if (verbose) for (Adresse ad : adresses)
            System.out.println("Found " + ad);

        // Kunden -------------------------------------------
        List<Kunde> kunden = kundeRepository.findAll("nickname");
        assertEquals(1,kunden.size());

        assertEquals (kunde, kunden.get(0));

        if (verbose) for (Kunde customer : kunden)
            System.out.println("Found " + customer);

        assertEquals(adresse, kunde.getAdresse());

        // Bestellungen --------------------------------------
        List<Bestellung> bestellungen = bestellungRepository.findAll("id");
        assertEquals(2,bestellungen.size());
        assertEquals(1, bestellung1.getPizzen().size());
        assertEquals(1, bestellung2.getPizzen().size());

        assertEquals(bestellung1.getKunde(), kunde);
        assertEquals(bestellung2.getKunde(), kunde);

        assertTrue(kunde.getBestellungen().contains(bestellung1));
        assertTrue(kunde.getBestellungen().contains(bestellung2));

        assertTrue(bestellungen.contains(bestellung1));
        assertTrue(bestellungen.contains(bestellung2));

        assertTrue(bestellung1.getPizzen().contains(pizza1));
        assertTrue(bestellung2.getPizzen().contains(pizza2));

        assertNotNull (kunde);
        assertNotNull(bestellung1);
        assertNotNull(bestellung2);

        if (verbose) for (Bestellung bestellung : bestellungen)
            System.out.println("Found " + bestellung);

        // Pizzen ---------------------------------------------
        List<Pizza> pizzen = pizzaRepository.findAll("id");
        assertEquals(2, pizzen.size());
        assertEquals(1, pizza1.getBestellungen().size());
        assertEquals(1, pizza2.getBestellungen().size());

        assertTrue(pizza1.getBestellungen().contains(bestellung1));
        assertTrue(pizza2.getBestellungen().contains(bestellung2));

        assertFalse(pizza1.getBestellungen().contains(bestellung2));
        assertFalse(pizza2.getBestellungen().contains(bestellung1));

        if (verbose) for (Pizza pizza : pizzen)
            System.out.println("Found " + pizza);
    }

    @Test
    public void e_remove ()
    {

        Transaction.begin();
        pizzaRepository.reset();
        bestellungRepository.reset();
        kundeRepository.reset();
        adresseRepository.reset();
        Transaction.commit();

        List<Adresse> adresses = adresseRepository.findAll("id");
        assertEquals(0,adresses.size());

        List<Kunde> kunden = kundeRepository.findAll("nickname");
        assertEquals(0,kunden.size());

        List<Bestellung> bestellungen = bestellungRepository.findAll("id");
        assertEquals(0,bestellungen.size());

        List<Pizza> pizzen = pizzaRepository.findAll("id");
        assertEquals(0, pizzen.size());

    }
}