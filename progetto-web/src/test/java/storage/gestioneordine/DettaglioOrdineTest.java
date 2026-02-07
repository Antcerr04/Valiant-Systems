package storage.gestioneordine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DettaglioOrdineTest {

    @Test
    void testGetterAndSetter() {
        DettaglioOrdine dettaglio = new DettaglioOrdine();

        int idProdotto = 10;
        String nomeProdotto = "Mouse Gaming";
        int quantita = 2;
        double prezzoUnitario = 25.50;
        String immagine = "mouse.jpg";

        dettaglio.setIdProdotto(idProdotto);
        dettaglio.setNomeProdotto(nomeProdotto);
        dettaglio.setQuantita(quantita);
        dettaglio.setPrezzoUnitario(prezzoUnitario);
        dettaglio.setImmagine(immagine);

        assertEquals(idProdotto, dettaglio.getIdProdotto());
        assertEquals(nomeProdotto, dettaglio.getNomeProdotto());
        assertEquals(quantita, dettaglio.getQuantita());
        assertEquals(prezzoUnitario, dettaglio.getPrezzoUnitario());
        assertEquals(immagine, dettaglio.getImmagine());
    }

    @Test
    void testGetTotale() {
        DettaglioOrdine dettaglio = new DettaglioOrdine();

        dettaglio.setQuantita(3);
        dettaglio.setPrezzoUnitario(19.99);

        double totaleAtteso = 3 * 19.99;

        assertEquals(totaleAtteso, dettaglio.getTotale(), 0.001);
    }
}