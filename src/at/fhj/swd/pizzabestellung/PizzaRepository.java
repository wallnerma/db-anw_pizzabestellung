package at.fhj.swd.pizzabestellung;

import at.fhj.swd.spize.Persistence;

import javax.persistence.TypedQuery;
import java.util.List;

public class PizzaRepository extends at.fhj.swd.persistence.Repository<Pizza>
        implements at.fhj.swd.persistence.IRepository<Pizza>
{

    public PizzaRepository() {super(Pizza.class);}

    public Pizza create (int id, String name, String groesse, float einzelpreis)
    {
        Pizza pizza = new Pizza(id , name, groesse, einzelpreis);
        entityManager.persist(pizza);

        return pizza;
    }

    public List<Pizza> findAllPizzasFromOrderId(int id) {
        TypedQuery<Pizza> query = entityManager.createNamedQuery (
                "Pizza.findAllPizzasFromOrderId"
                ,Pizza.class );
        query.setParameter("id", id);

        return query.getResultList();
    }

    void reset()
    {
        Persistence.resetTable(schema, junctionTable);
        Persistence.resetTable(schema, table);
    }

    static final String schema = "public";
    static final String table = "pizza";
    static final String junctionTable = BestellungRepository.junctionTable;
}