package database;

import java.util.List;

public class Nutzer{

    private int id;
    private String vorname;
    private String nachname;

    public Nutzer() {};

    public Nutzer(int id,String vorname,String nachname) {

        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    @Override
    public String toString() {
        return "Nutzer{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                '}';
    }

    /**
     * Methode um die Termine, in denen der Nutzer Teilnehmer ist, zurueckzugeben
     * @return Liste der Termine
     */
    public List<Termin> getTermine() {

        TerminDAO termin = new TerminDAO();

        return termin.getTermine(this);

    }

    /**
     * Methode um die Termine, zu denen der Nutzer eingeladet ist, zurueckzugeben
     * @return Liste der Termine
     */
    public List<Termin> getEinladungen() {

        EinladungDAO einladung = new EinladungDAO();

        return einladung.getEinladungen(this);

    }
}
