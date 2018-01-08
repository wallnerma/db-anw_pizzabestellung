package at.fhj.swd.pizzabestellung;

import at.fhj.swd.spize.Persistence;

import javax.persistence.TypedQuery;
import java.util.List;

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

//    public List<Bestellung> findStatusOfCustomer(String nickname) {
//        TypedQuery<Bestellung> query = entityManager.createNamedQuery (
//                "Bestellung.findStatusOfCustomer"
//                ,Bestellung.class );
//        query.setParameter("nickname", nickname);
//
//        return query.getResultList();
//    }

    void reset()
    {
        Persistence.resetTable(schema, junctionTable);
        Persistence.resetTable(schema, table);
    }

    static final String schema = "public";
    static final String table = "bestellung";
    static final String junctionTable = "bestellungpizza";
}