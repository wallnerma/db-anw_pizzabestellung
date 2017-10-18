package at.fhj.swd;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

@Entity class Pizza
{
    @Id private int id;
    private String name;
    private String größe;
    private float einzelpreis;

    @ManyToMany @JoinTable ( name = "bestellungpizza", joinColumns = @JoinColumn(name = "fk_pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_bestellung_id")) private Collection<Bestellung> bestellungen = new ArrayList<Bestellung>();

    protected Pizza(){}

    public Pizza(int id, String name, String größe, float einzelpreis) {
        setId(id);
        setName(name);
        setGröße(größe);
        setEinzelpreis(einzelpreis);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGröße() {
        return größe;
    }

    public void setGröße(String größe) {
        this.größe = größe;
    }

    public float getEinzelpreis() {
        return einzelpreis;
    }

    public void setEinzelpreis(float einzelpreis) {
        this.einzelpreis = einzelpreis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;

        Pizza pizza = (Pizza) o;

        return id == pizza.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void add (Bestellung bestellung)
    {
        bestellungen.add(bestellung);
        bestellung.add(this);
    }

    //TODO toString()
}
