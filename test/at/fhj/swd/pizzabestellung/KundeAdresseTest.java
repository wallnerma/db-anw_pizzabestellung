package at.fhj.swd.pizzabestellung;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class KundeAdresseTest {

    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "db_wallner16";

    static final String nickname = "terminator";
    static final Adresse johns_adresse = new Adresse(22, "Max Musterweg", 18, 8600, "Bruck/Mur");
    static final String nachname = "Miller";
    static final String vorname = "John";
    static final String telnummer = "06601234567";


    @BeforeClass
    public static void setup() {
        factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        assertNotNull(factory);
        manager = factory.createEntityManager();
        assertNotNull(manager);

        transaction = manager.getTransaction();
    }

    @AfterClass
    public static void teardown() {
        if (manager == null)
            return;

        manager.close();
        factory.close();
    }


    @Test
    public void create() {
        transaction.begin();

        Kunde john = new Kunde(nickname, johns_adresse, nachname, vorname, telnummer);

        assertEquals(johns_adresse, john.getAdresse());
        assertEquals(john, johns_adresse.getKunde());
        assertNotNull(john);
        assertNotNull(johns_adresse);
        manager.persist(johns_adresse);
        manager.persist(john);
        transaction.commit();

        System.out.println("Created and Persisted " + john);

    }

    @Test
    public void modify() {
        Kunde john = manager.find(Kunde.class, nickname);
        assertNotNull(john);
        System.out.println("Found " + john);

        transaction.begin();
        john.setNachname("Smith");
        john.setVorname("Will");
        john.setTelnummer("06649876321");
        transaction.commit();

        //#if STRICT
        //start from scratch - this ensures that john is fetched from the DB :
        teardown();
        setup();
        //#endif

        john = manager.find(Kunde.class, nickname);

        assertEquals("Smith", john.getNachname());
        assertEquals("Will", john.getVorname());
        assertEquals("06649876321", john.getTelnummer());

        System.out.println("Updated " + john);
    }

    @Test
    public void remove() {

        Kunde will = manager.find(Kunde.class, nickname);
        assertNotNull(will);

        Adresse wills_adresse = manager.find(Adresse.class, will.getAdresse().getId());
        assertNotNull(wills_adresse);

        transaction.begin();
        manager.remove(will);
        manager.remove(wills_adresse);
        transaction.commit();

        will = manager.find(Kunde.class, nickname);
        assertNull(will);
        wills_adresse = manager.find(Adresse.class, 22);
        assertNull(wills_adresse);

        System.out.println("Removed " + nickname);

    }

}