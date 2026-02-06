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
        Utente utente = new Utente("Antonio","Cerrone","Antcerr04","antoniocerrone132@gmail.com","Antonio2004@");

        assertEquals("Antonio", utente.getNome());
        assertEquals("Cerrone", utente.getCognome());
        assertEquals("Antcerr04", utente.getUsername());
        assertEquals("antoniocerrone132@gmail.com", utente.getEmail());
    }

}
