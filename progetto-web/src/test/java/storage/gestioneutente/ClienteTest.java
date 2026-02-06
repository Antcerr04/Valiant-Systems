package storage.gestioneutente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    /**
     * Method used to test a complete constructor
     */
    @Test
    void testCostruttoreCompleto() {
        Indirizzo indirizzo = new Indirizzo(
                "Via Roma",
                10,
                84022,
                "Campania",
                "Salerno",
                "Campagna"
        );

        Utente utente = new Utente(
                "Antonio",
                "Cerrone",
                "antonio123",
                "antonio@email.it",
                "password",
                indirizzo
        );

        assertEquals("Antonio", utente.getNome());
        assertEquals("Cerrone", utente.getCognome());
        assertEquals("antonio123", utente.getUsername());
        assertEquals("antonio@email.it", utente.getEmail());
        assertEquals(indirizzo, utente.getIndirizzo());

        // saldo of default
        assertEquals(5000.0, utente.getSaldo());
    }

    /**
     * Method used to test setSaldo
     */
    @Test
    void testSetSaldo() {
        Utente utente = new Utente();

        utente.setSaldo(200.0);

        assertEquals(200.0, utente.getSaldo());
    }

    /**
     * Method used to tes setIndirizzo
     */
    @Test
    void testSetIndirizzo() {
        Utente utente = new Utente();

        Indirizzo indirizzo = new Indirizzo(
                "Via Roma",
                10,
                84022,
                "Campania",
                "Salerno",
                "Campagna"
        );

        utente.setIndirizzo(indirizzo);

        assertEquals(indirizzo, utente.getIndirizzo());
    }
}
