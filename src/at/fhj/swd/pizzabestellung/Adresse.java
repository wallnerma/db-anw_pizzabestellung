package at.fhj.swd.pizzabestellung;

import javax.persistence.*;

@Entity

@NamedQueries({
        @NamedQuery(name = "Adresse.findAllAdressen",
                query = "SELECT a " +
                        "FROM Adresse a"),

        @NamedQuery(name = "Adresse.findSamePlz",
                query = "SELECT a  " +
                        "FROM Adresse a " +
                        "WHERE a.plz = :plz ")
})

public class Adresse {

    @Id
    private int id;
    private String strasse;
    private int hausnummer;
    private int plz;
    private String ort;

    @OneToOne(mappedBy = "adresse")
    private Kunde kunde;

    protected Adresse() {
    }

    public Adresse(int id, String strasse, int hausnummer, int plz, String ort) {
        setId(id);
        setStrasse(strasse);
        setHausnummer(hausnummer);
        setPlz(plz);
        setOrt(ort);
        setKunde(kunde);
    }

    private void setId(int id) {
        this.id = id;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public void setHausnummer(int hausnummer) {
        this.hausnummer = hausnummer;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public int getId() {
        return id;
    }

    public String getStrasse() {
        return strasse;
    }

    public int getHausnummer() {
        return hausnummer;
    }

    public int getPlz() {
        return plz;
    }

    public String getOrt() {
        return ort;
    }

    public Kunde getKunde() {
        return kunde;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adresse)) return false;

        Adresse adresse = (Adresse) o;

        return id == adresse.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
