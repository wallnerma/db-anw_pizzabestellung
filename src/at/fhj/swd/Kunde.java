package at.fhj.swd;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity public class Kunde {

    @Id private String nickname;
    private int fk_adresse_id;
    private String nachname;
    private String vorname;
    private String telnummer;

    protected Kunde() {}

    public Kunde(String nickname, int fk_adresse_id, String nachname, String vorname, String telnummer) {
        this.nickname = nickname;
        this.fk_adresse_id = fk_adresse_id;
        this.nachname = nachname;
        this.vorname = vorname;
        this.telnummer = telnummer;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFk_adresse_id(int fk_adresse_id) {
        this.fk_adresse_id = fk_adresse_id;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setTelnummer(String telnummer) {
        this.telnummer = telnummer;
    }

    public String getNickname() {
        return nickname;
    }

    public int getFk_adresse_id() {
        return fk_adresse_id;
    }

    public String getNachname() {
        return nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public String getTelnummer() {
        return telnummer;
    }

}
