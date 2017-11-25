package at.fhj.swd.pizzabestellung;


import java.util.List;

import static org.junit.Assert.*;
import        org.junit.BeforeClass;
import        org.junit.AfterClass;
import        org.junit.Test;

import at.fhj.swd.spize.Transaction;

import at.fhj.swd.persistence.Persistence;

@org.junit.FixMethodOrder( org.junit.runners.MethodSorters.NAME_ASCENDING)
public class AdresseKundeTestRepo {

    static final boolean verbose = true;

    static final int id = 10;
    static final String strasse = "Werk VI Straße";
    static final int hausnummer = 7;
    static final int plz = 8605;
    static final String ort = "Kapfenberg";

    static  final String    nickname        = "deadpool";
    static  final String    nachname        = "Wilson";
    static  final String    vorname         = "Wade";
    static  final String    telnummer       = "06601234567";

    static AdresseRepository adresseRepository;
    static KundeRepository kundeRepository;

    static Adresse adresse;
    static Kunde kunde;

    @BeforeClass
    public static void setup() {
        adresseRepository = new AdresseRepository();
        kundeRepository = new KundeRepository();

        Transaction.begin();
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

        assertNotNull(adresse);
        assertNotNull(kunde);

        Transaction.commit();

        if(verbose){
            System.out.println("Persisted " + adresse);
            System.out.println("Persisted " + kunde);
        }
    }

    @Test public void b_verify ()
    {
        // Adresse --------------------------------------

        List<Adresse> adressen = adresseRepository.findAll("id");
        assertEquals(1,adressen.size());

        adresse = adresseRepository.find(id);
        assertNotNull(adresse);

        assertEquals(adresse, adressen.get(0));

        assertEquals (kunde, adresse.getKunde() );

        if (verbose) for (Adresse ad : adressen)
            System.out.println("Found " + ad);

        // Kunde --------------------------------------

        List<Kunde> kunden = kundeRepository.findAll("nickname");
        assertEquals(1,kunden.size());

        assertEquals (kunde, kunden.get(0));

        if (verbose) for (Kunde customer : kunden)
            System.out.println("Found " + customer);

        assertEquals(adresse, kunde.getAdresse());
    }

    @Test
    public void c_queries() {
        System.out.println("--------findAllAdressen---------");
        List<Adresse> adressen = adresseRepository.findAllAdressen();
        assertEquals(1,adressen.size());

        for(Adresse addr : adressen) {
            System.out.println("Found " + addr);
        }

        System.out.println("--------findSamePlz---------");
        List<Adresse> samePlz = adresseRepository.findSamePlz(plz);
        assertEquals(1, adressen.size());

        for(Adresse addr : samePlz) {
            System.out.println("Found " + addr);
        }
    }

    @Test
    public void d_remove (){

        Transaction.begin();
        kundeRepository.reset();
        adresseRepository.reset();
        Transaction.commit();

        List<Adresse> adresses = adresseRepository.findAll("id");
        assertEquals(0,adresses.size());

        List<Kunde> kunden = kundeRepository.findAll("nickname");
        assertEquals(0,kunden.size());

    }
}