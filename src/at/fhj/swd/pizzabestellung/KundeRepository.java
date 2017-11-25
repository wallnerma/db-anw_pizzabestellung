package at.fhj.swd.pizzabestellung;

import at.fhj.swd.spize.Persistence;

import javax.persistence.TypedQuery;
import java.util.List;

public class KundeRepository extends at.fhj.swd.persistence.Repository<Kunde>
        implements at.fhj.swd.persistence.IRepository<Kunde>
{

    public KundeRepository() {super(Kunde.class);}

    public Kunde create (String nickname, Adresse adresse, String nachname, String vorname, String telnummer)
    {
        Kunde kunde = new Kunde(nickname, adresse, nachname, vorname, telnummer);
        entityManager.persist(kunde);

        return kunde;
    }

    public String findTelNumber(String firstName, String lastName) {
        TypedQuery<String> query = entityManager.createNamedQuery (
                "Kunde.findTelNumber"
                ,String.class );
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);

        return query.getSingleResult();
    }

    void reset()
    {
        Persistence.resetTable(schema, table);
    }

    static final String schema = "public";
    static final String table = "kunde";
}