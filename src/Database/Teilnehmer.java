package Database;

public class Teilnehmer {


    private Nutzer teilnehmer;
    private Termin termin;

    public Teilnehmer() {};

    public Teilnehmer(Nutzer teilnehmer, Termin termin) {

        this.teilnehmer = teilnehmer;
        this.termin = termin;
    }

    public Nutzer getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(Nutzer teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }
}
