package at.fhj.swd.persistence;

/*
 * project    company
 * subproject persistence
*/
        import java.util.List;

        import javax.persistence.EntityManager;
        import javax.persistence.TypedQuery;

public abstract class Repository<T>
{

    protected EntityManager entityManager;

    protected Class<T> entityClass = null;


    public Repository ( Class<T> entityClass)
    {
        this.entityClass = entityClass;

        entityManager = Persistence.connect ();
    }

    public Repository (String user, String password, Class<T> entityClass)
    {
        this.entityClass = entityClass;

        entityManager = Persistence.connect (user, password);
    }

    public T find(int id) {
        return entityManager.find ( entityClass , id);
    }


    public List<T> findAll() {
        TypedQuery<T> query = entityManager.createQuery (
                "SELECT entity FROM "
                        + entityClass.getTypeName() + " entity"
                        + " ORDER BY entity.id"
                ,entityClass);

        return query.getResultList();
    }


    public void printAll ()
    {
        List<T> entities = findAll();

        System.out.println ("All " + entityClass.getTypeName() + "s   -------------------------------");

        for (T entity : entities) System.out.println (entity);

        System.out.println ("End Of List   --------------------------------");
        System.out.println ();

    }
}
