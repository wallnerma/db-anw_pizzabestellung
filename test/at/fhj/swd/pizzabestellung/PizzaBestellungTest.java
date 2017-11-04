package at.fhj.swd.pizzabestellung;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;
import        org.junit.BeforeClass;
import        org.junit.AfterClass;
import        org.junit.Test;

public class PizzaBestellungTest
{
    static EntityManagerFactory factory;
    static EntityManager        manager;
    static EntityTransaction    transaction;

    static final String persistenceUnitName = "db_wallner16";

    static  final String    nickname        = "terminator";
    static  final Adresse   johns_adresse   = new Adresse(22, "Max Musterweg", 18, 8600, "Bruck/Mur");
    static  final String    nachname        = "Miller";
    static  final String    vorname         = "John";
    static  final String    telnummer       = "06601234567";

    static final int id1 = 23;
    static final int id2 = 24;
    static final String status1 = "in Arbeit";
    static final String status2 = "aufgenommen";

    static private int idPizza1 = 102;
    static private String name1 = "Salami";
    static private String groesse1 = "L";
    static private float einzelpreis1 = 27.20f;

    static private int idPizza2 = 145;
    static private String name2 = "Tonno";
    static private String groesse2 = "L";
    static private float einzelpreis2 = 20.25f;

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

        Kunde john = new Kunde (nickname, johns_adresse, nachname, vorname, telnummer);
        Bestellung order1 = new Bestellung(id1, status1);
        Bestellung order2 = new Bestellung(id2, status2);

        Pizza salami = new Pizza(idPizza1, name1, groesse1,einzelpreis1);
        Pizza tonno = new Pizza(idPizza2, name2, groesse2,einzelpreis2);


        order1.set(john);
        order2.set(john);

        salami.add(order1);
        tonno.add(order2);

        manager.persist (johns_adresse);
        manager.persist (john);
        manager.persist (order1);
        manager.persist (order2);
        manager.persist(salami);
        manager.persist(tonno);

        transaction.commit();

        teardown ();
        setup    ();

        john = manager.find(Kunde.class, nickname);
        order1 = manager.find(Bestellung.class, id1);
        order2 = manager.find(Bestellung.class, id2);
        salami = manager.find(Pizza.class, idPizza1);
        tonno = manager.find(Pizza.class, idPizza2);

        assertFalse(order1.getPizzen().contains(tonno));
        assertFalse(order2.getPizzen().contains(salami));

        assertTrue(order1.getPizzen().contains(salami));
        assertTrue(order2.getPizzen().contains(tonno));

        assertFalse(salami.getBestellungen().contains(order2));
        assertFalse(tonno.getBestellungen().contains(order1));

        assertTrue(salami.getBestellungen().contains(order1));
        assertTrue(tonno.getBestellungen().contains(order2));

        assertEquals(order1.getKunde(), john);
        assertEquals(order2.getKunde(), john);

        assertTrue(john.getBestellungen().contains(order1));
        assertTrue(john.getBestellungen().contains(order2));

        assertNotNull (john);
        assertNotNull(order1);
        assertNotNull(order2);

        System.out.println("Created and Persisted " + john);

    }

    @Test public void modify ()
    {
        Kunde john = manager.find (Kunde.class, nickname);
        assertNotNull (john);
        System.out.println("Found " + john);

        transaction.begin ();
        john.setNachname("Smith");
        john.setVorname("Will");
        john.setTelnummer("06649876321");
        transaction.commit();

        //#if STRICT
        //start from scratch - this ensures that john is fetched from the DB :
        teardown ();
        setup    ();
        //#endif

        john = manager.find (Kunde.class, nickname);

        assertEquals("Smith", john.getNachname());
        assertEquals("Will", john.getVorname());
        assertEquals("06649876321", john.getTelnummer());

        System.out.println("Updated " + john);
    }

    @Test public void remove ()
    {

        Kunde john = manager.find (Kunde.class, nickname);
        assertNotNull (john);

        Adresse johns_adresse = manager.find(Adresse.class, john.getAdresse().getId());
        assertNotNull(johns_adresse);

        transaction.begin ();

        for (Bestellung order : john.getBestellungen())
        {

            for(Pizza pizza : order.getPizzen())
            {
                manager.remove(pizza);
            }

            manager.remove (order);
        }



        manager.remove (john);
        manager.remove(johns_adresse);
        transaction.commit();

        john = manager.find(Kunde.class, nickname);
        assertNull (john);
        johns_adresse = manager.find(Adresse.class, 22);
        assertNull(johns_adresse);

        System.out.println("Removed " + nickname);

    }
}
