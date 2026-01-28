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

        Cliente cliente = new Cliente(
                "Antonio",
                "Cerrone",
                "antonio123",
                "antonio@email.it",
                "password",
                indirizzo
        );

        assertEquals("Antonio", cliente.getNome());
        assertEquals("Cerrone", cliente.getCognome());
        assertEquals("antonio123", cliente.getUsername());
        assertEquals("antonio@email.it", cliente.getEmail());
        assertEquals(indirizzo, cliente.getIndirizzo());

        // saldo of default
        assertEquals(500.0, cliente.getSaldo());
    }

    /**
     * Method used to test setSaldo
     */
    @Test
    void testSetSaldo() {
        Cliente cliente = new Cliente();

        cliente.setSaldo(200.0);

        assertEquals(200.0, cliente.getSaldo());
    }

    /**
     * Method used to tes setIndirizzo
     */
    @Test
    void testSetIndirizzo() {
        Cliente cliente = new Cliente();

        Indirizzo indirizzo = new Indirizzo(
                "Via Roma",
                10,
                84022,
                "Campania",
                "Salerno",
                "Campagna"
        );

        cliente.setIndirizzo(indirizzo);

        assertEquals(indirizzo, cliente.getIndirizzo());
    }
}
