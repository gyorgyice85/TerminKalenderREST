package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TerminDAO {


    public List<Termin> findAll() {
        List<Termin> list = new ArrayList<Termin>();
        Connection c = null;
        String sql = "SELECT * FROM Termin";
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


    public List<Termin> findByBeschreibung(String beschreibung) {
        List<Termin> list = new ArrayList<Termin>();
        Connection c = null;
        String sql = "SELECT * FROM Termin WHERE Beschreibung = ?";
        try {
            c = ConnectionHelper.getConnection();
            // prepareStatement creates a PreparedStatement object for sending
            // parameterized SQL statements to the database.
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "'%" + beschreibung + "%'");
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

    public Termin findById(int id) {
        String sql = "SELECT * FROM Termin WHERE id = ?";
        Termin termin = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                termin = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return termin;
    }


    public Termin create(Termin termin) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = ConnectionHelper.getConnection();
            ps = c.prepareStatement("INSERT INTO Termin (Beschreibung, Ort, Von, Bis) VALUES (?, ?, ?, ?)",
                    new String[] { "ID" });
            ps.setString(1, termin.getBeschreibung());
            ps.setString(2, termin.getOrt());
            ps.setTimestamp(3, termin.getVon());
            ps.setTimestamp(4, termin.getBis());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            // Update the id in the returned object. This is important as this value must be returned to the client.
            int id = rs.getInt(1);
            termin.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return termin;
    }

    public Termin save(Termin termin) {
        return termin.getId() > 0 ? update(termin) : create(termin);
    }


    public Termin update(Termin termin) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement
                    ("UPDATE Termin SET Beschreibung =?, Ort =?, Von =?, Bis =? WHERE id =?");
            ps.setString(1, termin.getBeschreibung());
            ps.setString(2, termin.getOrt());
            ps.setTimestamp(3, termin.getVon());
            ps.setTimestamp(4, termin.getBis());
            ps.setInt(5, termin.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionHelper.close(c);
        }
        return termin;
    }

    /**
    @return whether deleting the row was successful
    */
    public boolean remove(int id) {
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM Termin WHERE id=?");
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


    protected Termin processRow(ResultSet rs) throws SQLException {
        Termin termin = new Termin();
        termin.setId(rs.getInt("ID"));
        termin.setBeschreibung(rs.getString("Beschreibung"));
        termin.setOrt(rs.getString("Ort"));
        termin.setVon(rs.getTimestamp("Von"));
        termin.setBis(rs.getTimestamp("Bis"));


        return termin;
    }
}
