package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EinladungDAO {

    /**
     * Methode um die Nutzer, die fuer einen Termin eingeladet sind, zurueckzugeben
     * @param termin
     * @return List<Nutzer> die Liste der eingeladenen Nutzer
     */

    public List<Nutzer> getEingeladene(Termin termin){
        List<Nutzer> list = new ArrayList<Nutzer>();
        Connection c = null;
        NutzerDAO nutzer = new NutzerDAO();
        String sql = "SELECT NUTZER.* FROM NUTZER JOIN EINLADUNGEN WHERE " +
                "WEN = ID  AND TERMIN = ?";

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
     * Methode um die Einladungen der Nutzer zurueckzugeben
     * @param nutzer
     * @return List<Termin> die Liste Termine
     */
    public List<Termin> getEinladungen(Nutzer nutzer){
        List<Termin> list = new ArrayList<Termin>();
        Connection c = null;
        TerminDAO termin = new TerminDAO();
        String sql = "SELECT TERMIN.* FROM TERMIN JOIN EINLADUNGEN WHERE " +
                "TERMIN = ID AND WEN = ?";

        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, nutzer.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(termin.processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;



    }
}





