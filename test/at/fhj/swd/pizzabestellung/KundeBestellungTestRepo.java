package at.fhj.swd.pizzabestellung;


import java.util.List;

import static org.junit.Assert.*;
import        org.junit.BeforeClass;
import        org.junit.AfterClass;
import        org.junit.Test;

import at.fhj.swd.spize.Transaction;

import at.fhj.swd.persistence.Persistence;

@org.junit.FixMethodOrder( org.junit.runners.MethodSorters.NAME_ASCENDING)
public class KundeBestellungTestRepo {

    static final boolean verbose = true;

    static final int id = 158;
    static final String strasse = "Heimsiedlung";
    static final int hausnummer = 21;
    static final int plz = 8605;
    static final String ort = "Kapfenberg";

    static  final String    nickname        = "terminator";
    static  final String    nachname        = "Miller";
    static  final String    vorname         = "John";
    static  final String    telnummer       = "06601234567";

    static final int id1 = 23;
    static final int id2 = 24;
    static final String status1 = "in Arbeit";
    static final String status2 = "aufgenommen";

    static AdresseRepository adresseRepository;
    static KundeRepository kundeRepository;
    static BestellungRepository bestellungRepository;

    static Adresse adresse;
    static Kunde kunde;
    static Bestellung bestellung1;
    static Bestellung bestellung2;

    @BeforeClass
    public static void setup() {
        adresseRepository = new AdresseRepository();
        kundeRepository = new KundeRepository();
        bestellungRepository = new BestellungRepository();

        Transaction.begin();
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

        assertNotNull(adresse);
        assertNotNull(kunde);
        assertNotNull(bestellung1);
        assertNotNull(bestellung2);

        Transaction.commit();

        if(verbose){
            System.out.println("Persisted " + adresse);
            System.out.println("Persisted " + kunde);
            System.out.println("Persisted " + bestellung1);
            System.out.println("Persisted " + bestellung2);
        }
    }

    @Test public void b_join()
    {
        Transaction.begin();

        bestellung1.set(kunde);
        bestellung2.set(kunde);

        Transaction.commit();
    }

    @Test public void c_verify ()
    {
        // Adresse --------------------------------------

        List<Adresse> adresses = adresseRepository.findAll("id");
        assertEquals(1,adresses.size());

        adresse = adresseRepository.find(id);
        assertNotNull(adresse);

        assertEquals(adresse, adresses.get(0));

        assertEquals (kunde, adresse.getKunde() );

        if (verbose) for (Adresse ad : adresses)
            System.out.println("Found " + ad);

        // Kunde --------------------------------------

        List<Kunde> kunden = kundeRepository.findAll("nickname");
        assertEquals(1,kunden.size());

        assertEquals (kunde, kunden.get(0));

        if (verbose) for (Kunde customer : kunden)
            System.out.println("Found " + customer);

        assertEquals(adresse, kunde.getAdresse());

        // Bestellung --------------------------------------

        List<Bestellung> bestellungen = bestellungRepository.findAll("id");
        assertEquals(2,bestellungen.size());

        assertEquals(bestellung1.getKunde(), kunde);
        assertEquals(bestellung2.getKunde(), kunde);

        assertTrue(kunde.getBestellungen().contains(bestellung1));
        assertTrue(kunde.getBestellungen().contains(bestellung2));

        assertNotNull (kunde);
        assertNotNull(bestellung1);
        assertNotNull(bestellung2);

        if (verbose) for (Bestellung bestellung : bestellungen)
            System.out.println("Found " + bestellung);
    }

    @Test
    public void d_queries() {

        System.out.println("\n--------findTelNumber---------");
        String telNumber = kundeRepository.findTelNumber(vorname, nachname);

        assertEquals(telnummer, telNumber);

        System.out.println("Found " + telNumber + "\n");

        System.out.println("\n--------findStatusOfCustomer---------");
        List<Bestellung> customerStati = bestellungRepository.findStatusOfCustomer(nickname);
        assertEquals(2, customerStati.size());

        assertEquals(status1, customerStati.get(0).getStatus());
        assertEquals(status2, customerStati.get(1).getStatus());

        for(Bestellung order : customerStati) {
            System.out.println("Found " + order);
        }

    }

    @Test
    public void e_remove (){

        Transaction.begin();
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

    }
}