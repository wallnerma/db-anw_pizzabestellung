package at.fhj.swd.pizzabestellung;

import at.fhj.swd.spize.Persistence;
public class AdresseRepository extends at.fhj.swd.persistence.Repository<Adresse>
                               implements at.fhj.swd.persistence.IRepository<Adresse>
{

    public AdresseRepository() {super(Adresse.class);}

    public Adresse create (int id, String strasse, int hausnummer, int plz, String ort)
    {
        Adresse adresse = new Adresse(id,strasse,hausnummer,plz,ort);

        entityManager.persist(adresse);

        return adresse;
    }

    void reset()
    {
        Persistence.resetTable(schema, table);
    }

    static final String schema = "public";
    static final String table = "adresse";

}
