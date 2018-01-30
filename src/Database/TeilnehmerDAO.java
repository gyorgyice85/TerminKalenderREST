package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeilnehmerDAO {

    public List<Teilnehmer> findAll() {
        List<Teilnehmer> list = new ArrayList<Teilnehmer>();
        Connection c = null;
        String sql = "SELECT * FROM Nutzer";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }


    public List<Teilnehmer> findByName(String vorname, String nachname) {
        List<Teilnehmer> list = new ArrayList<Teilnehmer>();
        Connection c = null;
        String sql = "SELECT * FROM Nutzer WHERE Vorname = ? AND Nachname = ?";
        try {
            c = ConnectionHelper.getConnection();
            // prepareStatement creates a PreparedStatement object for sending
            // parameterized SQL statements to the database.
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "'%" + vorname + "%'");
            ps.setString(2,"'%" + nachname + "%'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return list;
    }

    public Teilnehmer findById(int id) {
        String sql = "SELECT * FROM Nutzer WHERE id = ?";
        Teilnehmer nutzer = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nutzer = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return nutzer;
    }


    public Teilnehmer create(Teilnehmer nutzer) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO Nutzer(Vorname, Nachname) VALUES (?, ?)",
                    new String[] { "ID" });
            ps.setString(1, nutzer.getVorname());
            ps.setString(2, nutzer.getNachname());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
            int id = rs.getInt(1);
            nutzer.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return nutzer;
    }

    public Teilnehmer save(Teilnehmer nutzer) {
        return nutzer.getId() > 0 ? update(nutzer) : create(nutzer);
    }


    public Teilnehmer update(Teilnehmer nutzer) {  //??
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement
                    ("UPDATE Nutzer SET Vorname =?, Nachname =? WHERE id =?");
            ps.setString(1, nutzer.getVorname());
            ps.setString(2, nutzer.getNachname());
            ps.setInt(3, nutzer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return nutzer;
    }

    /**
     @return whether deleting the row was successful
     */
    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM Nutzer WHERE id=?");
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
    }


    protected Teilnehmer processRow(ResultSet rs) throws SQLException {
        Teilnehmer nutzer = new Teilnehmer();
        nutzer.setId(rs.getInt("ID"));
        nutzer.setVorname(rs.getString("Vorname"));
        nutzer.setNachname(rs.getString("Nachname"));

        return nutzer;
    }
}
