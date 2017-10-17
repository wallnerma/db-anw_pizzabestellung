package at.fhj.swd;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity public class Bestellung
{
    @Id private int id;
    private String status;

    @ManyToOne @JoinColumn(name = "fk_kunde_nickname") private Kunde kunde;

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
}
