package at.fhj.swd;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity public class Adresse {

    @Id private int id;
    private String strasse;
    private int hausnummer;
    private int plz;
    private String ort;

    protected Adresse(){}

    public Adresse(int id, String strasse, int hausnummer, int plz, String ort)
    {
        setId(id);
        setStrasse(strasse);
        setHausnummer(hausnummer);
        setPlz(plz);
        setOrt(ort);
    }

    public void setId(int id) {
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

}
