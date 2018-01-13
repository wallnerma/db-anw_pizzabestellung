package at.fhj.swd.pizzabestellung;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import at.fhj.swd.spize.Transaction;
import at.fhj.swd.persistence.Persistence;
import at.fhj.swd.spize.persistence.config.PersistenceUnitProperties;

import javax.persistence.EntityManager;
import java.util.List;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class PizzaBestellungReaderTest extends SQLUserTest {

    final static String user = "pizzabestellung_reader";
    final static String password = "reader";

    static void reset() {
        Persistence.close();
        EntityManager em = Persistence.connect();

        reset(em);

        teardown();
    }

    static void create() {
        Persistence.close();
        EntityManager em = Persistence.connect();

        create(em);
    }

    @BeforeClass
    public static void setup() {

        System.out.println(
                "Connecting to " + PersistenceUnitProperties.getUrl());

        Persistence.connect(user, password);

        adresseRepository = new AdresseRepository();
        kundeRepository = new KundeRepository();
    }

    @AfterClass
    public static void teardown() {
        Persistence.close();
    }

    @Test
    public void a_createWithReader() {

        Transaction.begin();

        try {
            adresse = adresseRepository.create(id, strasse, hausnummer, plz, ort);
            Transaction.commit();

            fail();
        } catch (Exception ex) {
            assertTrue(permissionDenied(ex));
        }
    }

    @Test
    public void b_create() {
        create();
    }

    @Test
    public void c_modify() {

        // Adresse --------------------------------------
        Transaction.begin();

        List<Adresse> adressen = adresseRepository.findAll("id");
        assertEquals(1, adressen.size());

        adresse = adresseRepository.find(id);
        assertNotNull(adresse);

        adressen.get(0).setPlz(8522);

        try {
            Transaction.commit();

            fail();
        } catch (Exception ex) {
            assertTrue(permissionDenied(ex));
        }

    }

    @Test
    public void d_removeWithWriter() {
        try {
            reset();
        } catch (Exception ex) {
            assertTrue(permissionDenied(ex));
            Transaction.rollback();
        }
    }

    @Test
    public void e_remove() {
        reset();
    }
}