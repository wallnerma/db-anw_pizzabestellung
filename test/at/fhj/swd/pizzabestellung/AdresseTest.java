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
public class AdresseTest {

    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "db_wallner16";

    static final int id = 158;
    static final String strasse = "Heimsiedlung";
    static final int hausnummer = 21;
    static final int plz = 8605;
    static final String ort = "Kapfenberg";

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
        Adresse kberg = new Adresse(id, strasse, hausnummer, plz, ort);
        assertNotNull(kberg);
        manager.persist(kberg);
        transaction.commit();

        System.out.println("Created and Persisted " + kberg);

    }

    @Test
    public void modify() {
        Adresse kberg = manager.find(Adresse.class, id);
        assertNotNull(kberg);
        System.out.println("Found " + kberg);

        transaction.begin();
        kberg.setStrasse("Klosterweg");
        kberg.setHausnummer(16);
        kberg.setPlz(8600);
        kberg.setOrt("Bruck/Mur");
        transaction.commit();

        teardown();
        setup();

        kberg = manager.find(Adresse.class, id);

        assertEquals("Klosterweg", kberg.getStrasse());
        assertEquals(16, kberg.getHausnummer());
        assertEquals(8600, kberg.getPlz());
        assertEquals("Bruck/Mur", kberg.getOrt());

        System.out.println("Updated " + kberg);
    }

    @Test
    public void remove() {
        Adresse kberg = manager.find(Adresse.class, id);
        assertNotNull(kberg);

        transaction.begin();
        manager.remove(kberg);
        transaction.commit();

        kberg = manager.find(Adresse.class, id);
        assertNull(kberg);

        System.out.println("Removed " + id);

    }

}
