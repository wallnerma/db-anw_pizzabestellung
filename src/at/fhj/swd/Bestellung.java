package at.fhj.swd;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity public class Bestellung
{
    @Id private int id;
    private String status;

    @ManyToOne @JoinColumn(name = "fk_kunde_nickname") private Kunde kunde;
    @ManyToMany (mappedBy = "bestellungen") private Collection<Pizza> pizzen = new ArrayList<Pizza>();

    protected Bestellung(){}

    public Bestellung(int id, String status)
    {
        setId(id);
        setStatus(status);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Kunde getKunde()
    {
        return kunde;
    }

    public void set(Kunde kunde)
    {
        this.kunde = kunde;
        kunde.add(this);
    }

    public void add(Pizza pizza)
    {
        pizzen.add(pizza);

    }
}
