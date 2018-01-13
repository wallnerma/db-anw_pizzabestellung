package at.fhj.swd.pizzabestellung;

import static org.junit.Assert.*;
import        org.junit.BeforeClass;
import        org.junit.AfterClass;
import        org.junit.Test;

import  at.fhj.swd.spize.Transaction;
import at.fhj.swd.persistence.Persistence;
import  at.fhj.swd.spize.persistence.config.PersistenceUnitProperties;

import java.util.List;

public class PizzaBestellungAdminTest extends SQLUserTest {

    final static String user     = "pizzabestellung_admin";
    final static String password = "admin";

    @BeforeClass public static void setup()
    {

        System.out.println (
                "Connecting to " + PersistenceUnitProperties.getUrl ());

        Persistence.connect (user, password);

        adresseRepository = new AdresseRepository();
        kundeRepository = new KundeRepository();
    }

    @AfterClass public static void teardown()
    {
        Persistence.close ();
    }

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

    @Test public void b_modify()
    {
        // Adresse --------------------------------------

        Transaction.begin();

        List<Adresse> adressen = adresseRepository.findAll("id");
        assertEquals(1,adressen.size());

        adresse = adresseRepository.find(id);
        assertNotNull(adresse);

        adressen.get(0).setPlz(8522);

        Transaction.commit();

    }


    @Test
    public void c_remove (){

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
