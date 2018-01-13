package at.fhj.swd.pizzabestellung;

import static org.junit.Assert.*;
import        org.junit.BeforeClass;
import        org.junit.AfterClass;
import        org.junit.Test;

import  at.fhj.swd.spize.Transaction;
import at.fhj.swd.persistence.Persistence;
import  at.fhj.swd.spize.persistence.config.PersistenceUnitProperties;

import  at.fhj.swd.pizzabestellung.AdresseRepository;
import  at.fhj.swd.pizzabestellung.BestellungRepository;
import  at.fhj.swd.pizzabestellung.KundeRepository;
import  at.fhj.swd.pizzabestellung.PizzaRepository;

import javax.persistence.EntityManager;
import java.util.List;

@org.junit.FixMethodOrder( org.junit.runners.MethodSorters.NAME_ASCENDING)
public class PizzaBestellungWriterTest extends SQLUserTest {

    final static String user     = "pizzabestellung_writer";
    final static String password = "writer";

    static void reset()
    {
        Persistence.close();
        EntityManager em = Persistence.connect();

        reset(em);

        teardown();
    }

    @BeforeClass public static void setup()
    {

        System.out.println (
                "Connecting to " + PersistenceUnitProperties.getUrl ());

        Persistence.connect (user, password);

        adresseRepository = new AdresseRepository();
        kundeRepository = new KundeRepository();
        bestellungRepository = new BestellungRepository();
        pizzaRepository = new PizzaRepository();
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
    public void c_removeWithWriter (){
        try
        {
            reset();
        }
        catch (Exception ex)
        {
            assertTrue(permissionDenied(ex));
            Transaction.rollback();
        }
    }

    @Test
    public void d_remove (){
        reset();
    }
}
