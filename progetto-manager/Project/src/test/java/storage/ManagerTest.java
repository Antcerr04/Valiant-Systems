package storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {

    /**
     * Method used to test complete constructor
     */
    @Test
    public void testCostruttoreFull(){
        Manager manager = new Manager("Antonio","Cerrone","Antcerr04","antoniocerrone132@gmail.com","Antonio2004@");

        assertEquals("Antonio", manager.getNome());
        assertEquals("Cerrone", manager.getCognome());
        assertEquals("Antcerr04", manager.getUsername());
        assertEquals("antoniocerrone132@gmail.com", manager.getEmail());
        assertEquals("Antonio2004@", manager.getPassword());
    }

    /**
     * Method used to test isManager Function
     */
    @Test
    public void testIsManager(){
        Manager manager = new Manager("Antonio","Cerrone","Antcerr04","antoniocerrone132@gmail.com");

        assertTrue(manager.isManager());
    }

    /**
     * Method used to test setters
     */
    @Test
    public void testSetter(){
        Manager manager = new Manager();

        assertTrue(manager.isManager());
        manager.setNome("Antonio");
        manager.setCognome("Cerrone");
        manager.setUsername("Antcerr04");
        manager.setEmail("antoniocerrone132@gmail.com");
        manager.setPassword("Antonio2004@");

        assertEquals("Antonio", manager.getNome());
        assertEquals("Cerrone", manager.getCognome());
        assertEquals("Antcerr04", manager.getUsername());
        assertEquals("antoniocerrone132@gmail.com", manager.getEmail());
        assertEquals("Antonio2004@", manager.getPassword());
    }

    /**
     * Method used to test nome
     */

    @Test
    public void testSetNome(){
        assertTrue(Manager.validateNome("Antonio"));
        assertFalse(Manager.validateNome(""));
        assertFalse(Manager.validateNome(null));
        assertFalse(Manager.validateNome("Ant0nio"));
        assertFalse(Manager.validateNome("Antoni@"));

    }

    /**
     * Method used to test cognome
     */
    @Test
    public void testSetCognome(){
        assertTrue(Manager.validateCognome("Cerrone"));
        assertFalse(Manager.validateCognome(null));
        assertFalse(Manager.validateCognome(""));
        assertFalse(Manager.validateCognome("Cerr0ne"));
        assertFalse(Manager.validateCognome("Cerrone@"));

    }

    /**
     * Method used to test username
     */
    @Test
    public void testSetUsername(){
        assertTrue(Manager.validateUsername("Antcerr04"));
        assertFalse(Manager.validateUsername(null));
        assertFalse(Manager.validateUsername(""));
        assertFalse(Manager.validateUsername("ac"));
        assertFalse(Manager.validateUsername("antoniocerrone132@"));
    }

    /**
     * Method used to test email
     */

    @Test
    public void testSetEmail(){
        assertTrue(Manager.validateEmail("antoniocerrone132@gmail.com"));
        assertFalse(Manager.validateEmail(null));
        assertFalse(Manager.validateEmail(""));
        assertFalse(Manager.validateEmail("ac"));
        assertFalse(Manager.validateEmail("antoniocerrone132gmail.com"));
        assertFalse(Manager.validateEmail("antoniocerrone132@gmail"));

    }

    /**
     * Method used to test password
     */

    @Test
    public void testSetPassword(){
        assertTrue(Manager.validatePassword("Antonio2004@"));
        assertFalse(Manager.validatePassword(null));
        assertFalse(Manager.validatePassword(""));
        assertFalse(Manager.validatePassword("ac"));
        assertFalse(Manager.validatePassword("Antonio2004"));
        assertFalse(Manager.validatePassword("antonio2004@"));
        assertFalse(Manager.validatePassword("Antonio@"));

    }

}
