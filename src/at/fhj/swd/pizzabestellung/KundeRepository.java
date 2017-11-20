package at.fhj.swd.pizzabestellung;

import at.fhj.swd.spize.Persistence;
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

    void reset()
    {
        Persistence.resetTable(schema, table);
    }

    static final String schema = "public";
    static final String table = "kunde";
}