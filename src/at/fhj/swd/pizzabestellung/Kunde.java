package at.fhj.swd.pizzabestellung;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity public class Kunde
{

    @Id private String nickname;
    private String nachname;
    private String vorname;
    private String telnummer;

    @OneToOne @JoinColumn(name = "fk_adresse_id") private Adresse adresse;
    @OneToMany (mappedBy = "kunde") private Collection<Bestellung> bestellungen = new ArrayList<Bestellung>();

    protected Kunde() {}

    public Kunde(String nickname, Adresse adresse, String nachname, String vorname, String telnummer)
    {
        setNickname(nickname);
        setAdresse(adresse);
        setNachname(nachname);
        setVorname(vorname);
        setTelnummer(telnummer);
    }

    private void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public void setAdresse(Adresse adresse)
    {
        this.adresse = adresse;
        adresse.setKunde(this);
    }

    public void setNachname(String nachname)
    {
        this.nachname = nachname;
    }

    public void setVorname(String vorname)
    {
        this.vorname = vorname;
    }

    public void setTelnummer(String telnummer)
    {
        this.telnummer = telnummer;
    }

    public void setBestellungen(Collection<Bestellung> bestellungen) {
        this.bestellungen = bestellungen;
    }

    public String getNickname()
    {
        return nickname;
    }

    public Adresse getAdresse()
    {
        return adresse;
    }

    public String getNachname()
    {
        return nachname;
    }

    public String getVorname()
    {
        return vorname;
    }

    public String getTelnummer()
    {
        return telnummer;
    }

    public Collection<Bestellung> getBestellungen() {
        return bestellungen;
    }

    void add(Bestellung bestellung)
    {
        bestellungen.add(bestellung);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kunde)) return false;

        Kunde kunde = (Kunde) o;

        return nickname.equals(kunde.nickname);
    }

    @Override
    public int hashCode() {
        return nickname.hashCode();
    }

    //TODO toString()

}
