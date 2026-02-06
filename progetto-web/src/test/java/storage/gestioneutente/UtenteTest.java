package storage.gestioneutente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtenteTest {
    /**
     * Method used to test complete constructor
     */
    @Test
    public void constructorTest() {
        Utente utente = new Utente("Antonio","Cerrone","Antcerr04","cerrone812@gmail.com","Antonio2004@");
        utente.setId(1);
        utente.setRuolo("cliente");
        assertTrue(utente.getNome().equals("Antonio"));

          assertEquals(utente.getEmail(),"cerrone812@gmail.com");
                 assertEquals(utente.getPassword(),"Antonio2004@");
                 assertEquals(utente.getCognome(),"Cerrone");
                 assertEquals(utente.getUsername(),"Antcerr04");
                 assertEquals(utente.getPassword(), "Antonio2004@");
                 assertEquals(utente.getRuolo(),"cliente");
                 assertEquals(utente.getId(),1);


    }

    /**
     * Method used to test empty constructor
     */
    @Test
    public void emptyConstructorTest() {
        Utente utente = new Utente();
        utente.setNome("Antonio");
        utente.setCognome("Cerrone");
        utente.setUsername("Antcerr04");
        utente.setEmail("cerrone812@gmail.com");
        utente.setPassword("Antonio2004@");
        utente.setPasswordHash("Antonio2004@");

        assertEquals(utente.getNome(),"Antonio");
        assertEquals(utente.getEmail(),"cerrone812@gmail.com");
        assertEquals(utente.getPassword(),"Antonio2004@");
        assertEquals(utente.getCognome(),"Cerrone");
        assertEquals(utente.getUsername(),"Antcerr04");


    }

    /**
     * Method used to test nome
     */
    @Test
    public void nomeTest() {
        assertTrue(Utente.validateNome("Antonio"));
        assertFalse(Utente.validateNome("Antcer04"));
        assertFalse(Utente.validateNome(""));
        assertFalse(Utente.validateNome(null));
    }

    /**
     * Method used to test cognome
     */
    @Test
    public void cognomeTest() {
        assertTrue(Utente.validateCognome("Cerrone"));
        assertFalse(Utente.validateCognome("Antcer04"));
        assertFalse(Utente.validateCognome(""));
        assertFalse(Utente.validateCognome(null));
    }

    /**
     * Method used to test username
     */
    @Test
    public void usernameTest() {
        assertTrue(Utente.validateUsername("Ancerr04"));
        assertFalse(Utente.validateUsername("Antcerr@"));
        assertFalse(Utente.validateUsername(""));
        assertFalse(Utente.validateUsername(null));

    }

    /**
     * Method used to test email
     */
    @Test
    public void emailTest() {
        assertTrue(Utente.validateEmail("cerrone812@gmail.com"));
        assertFalse(Utente.validateEmail("cerrone812gmail.com"));
        assertFalse(Utente.validateEmail(""));
        assertFalse(Utente.validateEmail(null));
        assertFalse(Utente.validateEmail("cerrone812@gmial"));
        assertFalse(Utente.validateEmail("cerrone81#2@gmail.com"));
    }

    /**
     * Method used to test password
     */
    @Test
    public void passwordTest() {
        assertTrue(Utente.validatePassword("Antonio2004@"));
        assertFalse(Utente.validatePassword(""));
        assertFalse(Utente.validatePassword(null));
        assertFalse(Utente.validatePassword("Antonio2004"));
        assertFalse(Utente.validatePassword("antonio2004@"));
        assertFalse(Utente.validatePassword("Antonio@"));
        assertFalse(Utente.validatePassword("Anto"));
    }

}
