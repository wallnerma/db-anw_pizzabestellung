package at.fhj.swd;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity public class Kunde
{

    @Id private String nickname;
    private String nachname;
    private String vorname;
    private String telnummer;

    @OneToOne @JoinColumn(name = "fk_adresse_id") private Adresse adresse;

    protected Kunde() {}

    public Kunde(String nickname, Adresse adresse, String nachname, String vorname, String telnummer)
    {
        setNickname(nickname);
        setAdresse(adresse);
        setNachname(nachname);
        setVorname(vorname);
        setTelnummer(telnummer);
    }

    public void setNickname(String nickname)
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
