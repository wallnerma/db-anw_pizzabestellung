package at.fhj.swd;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.ws.soap.Addressing;

import static org.junit.Assert.*;
import        org.junit.BeforeClass;
import        org.junit.AfterClass;
import        org.junit.Test;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class TestKunde {

    static EntityManagerFactory factory;
    static EntityManager        manager;
    static EntityTransaction    transaction;

    static final String persistenceUnitName = "db_wallner16";

    static  final String    nickname        = "terminator";
    static  final Adresse   adresse         = new Adresse(22, "Max Musterweg", 18, 8600, "Bruck/Mur");
    static  final String    nachname        = "Wresnik";
    static  final String    vorname         = "Georg";
    static  final String    telnummer       = "06601234567";

    @BeforeClass public static void setup()
    {
        factory = Persistence.createEntityManagerFactory( persistenceUnitName );
        assertNotNull (factory);
        manager  = factory.createEntityManager();
        assertNotNull (manager);

        transaction = manager.getTransaction();
    }

    @AfterClass public static void teardown()
    {
        if (manager == null)
            return;

        manager.close();
        factory.close();
    }


    @Test public void create()
    {
        transaction.begin ();

        Kunde gw = new Kunde (nickname, adresse, nachname, vorname, telnummer);

        assertNotNull (gw);
        manager.persist (gw);
        transaction.commit();

        System.out.println("Created and Persisted " + gw);

    }

    @Test public void modify ()
    {
        Kunde gw = manager.find (Kunde.class, nickname);
        assertNotNull (gw);
        System.out.println("Found " + gw);

        transaction.begin ();
        gw.setNachname("Wallner");
        gw.setVorname("Marcus");
        gw.setTelnummer("06649876321");
        transaction.commit();

        //#if STRICT
        //start from scratch - this ensures that kberg is fetched from the DB :
        teardown ();
        setup    ();
        //#endif

        gw = manager.find (Kunde.class, nickname);

        assertEquals("Wallner", gw.getNachname());
        assertEquals("Marcus", gw.getVorname());
        assertEquals("06649876321", gw.getTelnummer());

        System.out.println("Updated " + gw);
    }

    @Test public void remove ()
    {
        Kunde gw = manager.find (Kunde.class, nickname);
        assertNotNull (gw);

        Adresse addr = manager.find(Adresse.class, fk_adresse_id);
        assertNotNull(addr);

        transaction.begin ();
        manager.remove (gw);
        manager.remove(addr);
        transaction.commit();

        gw = manager.find(Kunde.class, nickname);
        assertNull (gw);
        addr = manager.find(Adresse.class, fk_adresse_id);
        assertNull(addr);

        System.out.println("Removed " + nickname);

    }

}

