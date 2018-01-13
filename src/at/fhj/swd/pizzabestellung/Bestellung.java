package at.fhj.swd.pizzabestellung;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity

@NamedQueries({
        @NamedQuery(name = "Bestellung.findStatusOfCustomer",
                query = "SELECT b " +
                        "FROM Bestellung b " +
                        "JOIN Kunde k " +
                        "WHERE k.nickname = :nickname")

})

public class Bestellung {
    @Id
    private int id;
    private String status;

    @ManyToOne
    @JoinColumn(name = "fk_kunde_nickname")
    private Kunde kunde;
    @ManyToMany(mappedBy = "bestellungen")
    private Collection<Pizza> pizzen = new ArrayList<Pizza>();

    protected Bestellung() {
    }

    public Bestellung(int id, String status) {
        setId(id);
        setStatus(status);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void set(Kunde kunde) {
        this.kunde = kunde;
        kunde.add(this);
    }

    void add(Pizza pizza) {
        pizzen.add(pizza);
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Collection<Pizza> getPizzen() {
        return pizzen;
    }

    public void setPizzen(Collection<Pizza> pizzen) {
        this.pizzen = pizzen;
    }
}
