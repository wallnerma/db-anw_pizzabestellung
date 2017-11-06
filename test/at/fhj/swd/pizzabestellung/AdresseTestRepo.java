package at.fhj.swd.pizzabestellung;


import java.util.List;

import static org.junit.Assert.*;
import        org.junit.BeforeClass;
import        org.junit.AfterClass;
import        org.junit.Test;

import at.fhj.swd.spize.Transaction;

import at.fhj.swd.persistence.Persistence;

@org.junit.FixMethodOrder( org.junit.runners.MethodSorters.NAME_ASCENDING)
public class AdresseTestRepo {

    static final boolean verbose = true;

    static final int id = 158;
    static final String strasse = "Heimsiedlung";
    static final int hausnummer = 21;
    static final int plz = 8605;
    static final String ort = "Kapfenberg";

    static AdresseRepository adresseRepository;

    static Adresse adresse;

    @BeforeClass
    public static void setup() {
        adresseRepository = new AdresseRepository();

        Transaction.begin();
        adresseRepository.reset();
        Transaction.commit();
    }

    @AfterClass
    public static void teardown() { Persistence.close();}

    @Test public void create () {
        Transaction.begin();

        adresse = adresseRepository.create(id,strasse,hausnummer,plz,ort);

        assertNotNull(adresse);

        Transaction.commit();

        if(verbose) System.out.println("Persited " + adresse);

    }

    @Test public void verify ()
    {
        List<Adresse> adresses = adresseRepository.findAll();
        assertEquals(1,adresses.size());

        adresse = adresseRepository.find(id);
        assertNotNull(adresse);

        assertEquals(adresse, adresses.get(0));

        if (verbose) for (Adresse ad : adresses)
            System.out.println("Found " + ad);
    }
}