package storage.gestioneutente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagerTest {

    /**
     * Method used to test complete constructor
     */
    @Test
    public void testCostruttore(){
        Manager manager = new Manager("Antonio","Cerrone","Antcerr04","antoniocerrone132@gmail.com","Antonio2004@");

        assertEquals("Antonio", manager.getNome());
        assertEquals("Cerrone", manager.getCognome());
        assertEquals("Antcerr04", manager.getUsername());
        assertEquals("antoniocerrone132@gmail.com", manager.getEmail());
    }

    /**
     * Method used to test method isManager
     */
    @Test
    public void testIsManager(){
        Manager manager = new Manager();

        assertTrue(manager.isManager());
    }

}
