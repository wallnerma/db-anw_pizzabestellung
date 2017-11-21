package at.fhj.swd.pizzabestellung;

import at.fhj.swd.spize.Persistence;
public class BestellungRepository extends at.fhj.swd.persistence.Repository<Bestellung>
        implements at.fhj.swd.persistence.IRepository<Bestellung>
{

    public BestellungRepository() {super(Bestellung.class);}

    public Bestellung create (int id, String status)
    {
        Bestellung bestellung = new Bestellung(id, status);
        entityManager.persist(bestellung);

        return bestellung;
    }

    void reset()
    {
        Persistence.resetTable(schema, junctionTable);
        Persistence.resetTable(schema, table);
    }

    static final String schema = "public";
    static final String table = "bestellung";
    static final String junctionTable = "bestellungpizza";
}