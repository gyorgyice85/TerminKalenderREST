package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeilnehmerDAO {

    /**
     * Methode um die Nutzer, die fuer einen Termin eingeladet sind, zurueckzugeben
     * @param termin
     * @return List<Nutzer> die Liste der eingeladenen Nutzer
     */

    public List<Nutzer> getTeilnehmer(Termin termin) {
        List<Nutzer> list = new ArrayList<Nutzer>();
        Connection c = null;
        NutzerDAO nutzer = new NutzerDAO();
        String sql = "SELECT NUTZER.* FROM NUTZER JOIN TEILNEHMER WHERE " +
                "Teilnehmer.NutzerID = ID  AND TerminID = ?";

        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, termin.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(nutzer.processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }


    /**
     * Die Metode fuegt einen neuen Teilhemner zur Teilnehmerliste hinzu
     * @param nutzer der neue Teilnehmer
     * @param termin
     */
    public void create(Nutzer nutzer, Termin termin){

        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO Teilnehmer(NutzerID, TerminID) VALUES (?, ?)");
            ps.setInt(1, nutzer.getId());
            ps.setInt(2, termin.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
    }

    /**
     * Die Methode loescht den Teilnehmer des Termins
     * @param nutzer der loeschende Teilnehmer
     * @param termin
     * @return
     */
    public boolean remove(Nutzer nutzer, Termin termin){
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement
                    ("DELETE FROM Teilnehmer WHERE NutzerID =? AND TerminID = ?");
            ps.setInt(1, nutzer.getId());
            ps.setInt(2, termin.getId());
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
    }
    

    Nutzer processRow(ResultSet rs) throws SQLException {
        Nutzer nutzer = new Nutzer();
        nutzer.setId(rs.getInt("ID"));
        nutzer.setVorname(rs.getString("Vorname"));
        nutzer.setNachname(rs.getString("Nachname"));

        return nutzer;
    }
}
