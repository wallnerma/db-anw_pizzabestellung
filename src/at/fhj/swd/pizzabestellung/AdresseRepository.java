package at.fhj.swd.pizzabestellung;

import at.fhj.swd.spize.Persistence;

import javax.persistence.TypedQuery;
import java.util.List;

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

    public List<Adresse> findAllAdressen()
    {

        TypedQuery<Adresse> query = entityManager.createNamedQuery (
                "Adresse.findAllAdressen"
                ,Adresse.class );

        //query.setParameter("projectName", projectName);
        return query.getResultList ();
    }

    public List<Adresse> findSamePlz(int plz) {
        TypedQuery<Adresse> query = entityManager.createNamedQuery (
                "Adresse.findSamePlz"
                ,Adresse.class );
        query.setParameter("plz", plz);

        return query.getResultList();
    }

    void reset()
    {
        Persistence.resetTable(schema, table);
    }

    static final String schema = "public";
    static final String table = "adresse";
}
