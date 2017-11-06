

package at.fhj.swd.persistence;

import javax.persistence.EntityManager;

public class Persistence extends at.fhj.swd.spize.Persistence
{

    public static final String persistenceUnit = "db_wallner16";


    public static EntityManager connect ()
    {
        return connect (persistenceUnit);
    }

    public static EntityManager connect (String user, String password)
    {
        return connect (persistenceUnit, user, password);
    }
}
