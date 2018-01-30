package Database;

import com.sun.xml.internal.bind.v2.model.core.ID;

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
}
