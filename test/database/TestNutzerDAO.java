package database;

import org.junit.Before;
import org.junit.Test;

public class TestNutzerDAO {

    NutzerDAO personDAO;

    @Before
    public void setUp() {
        personDAO = new NutzerDAO();
    }

    @Test
    public void testListAll() {
        for (Nutzer p : personDAO.findAll()) {
            System.out.println(p);
        }
    }
}
