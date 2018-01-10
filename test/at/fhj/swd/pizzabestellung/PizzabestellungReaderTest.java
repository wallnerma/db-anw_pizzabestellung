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

public class PizzabestellungReaderTest extends SQLUserTest {

    final static String user     = "pizzabestellung_reader";
    final static String password = "reader";

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

    @Test public void create ()
    {
        Transaction.begin();

        try
        {
            pizzaRepository.create(55, "must", "fail",0f);
            Transaction.commit();

            fail ();
        }
        catch (Exception exc)
        {
            System.out.println (exc);

            assertTrue (permissionDenied (exc));
            //Transaction.rollback();
        }
    }
// ToDO
//    @Test public void modify ()
//    {
//
//        Transaction.begin();
//
//
//
//        FullTimeEmployee fte_1003 = (FullTimeEmployee)
//                empRepository.find (emp1003_firstName
//                        ,emp1003_lastName );
//        assertTrue (fte_1003.getId() != 0);
//
//        fte_1003.setSalary ( 0 );
//
//        try
//        {   Transaction.commit();
//
//            fail ();
//        }
//        catch (Exception exc)
//        {
//            assertTrue (permissionDenied (exc));
//        }
//    }



}
