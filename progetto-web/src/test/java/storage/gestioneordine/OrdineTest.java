package storage.gestioneordine;

import org.junit.jupiter.api.Test;
import storage.gestioneutente.Indirizzo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrdineTest {
    @Test
    void testOrdine() {
        Ordine ordine = new Ordine();

        int id = 1;
        String nome = "Mario";
        String cognome = "Rossi";
        Indirizzo indirizzo = new Indirizzo();
        String trackID = "TRACK123";
        Timestamp dataOrdine = new Timestamp(System.currentTimeMillis());
        List<DettaglioOrdine> dettagli = new ArrayList<>();
        double totaleOrdine = 99.99;
        int statoOrdine = 2;

        ordine.setId(id);
        ordine.setNome(nome);
        ordine.setCognome(cognome);
        ordine.setIndirizzo(indirizzo);
        ordine.setTrackID(trackID);
        ordine.setDataOrdine(dataOrdine);
        ordine.setDettaglioOrdineList(dettagli);
        ordine.setTotaleOrdine(totaleOrdine);
        ordine.setStatoOrdine(statoOrdine);

        assertEquals(id, ordine.getId());
        assertEquals(nome, ordine.getNome());
        assertEquals(cognome, ordine.getCognome());
        assertEquals(indirizzo, ordine.getIndirizzo());
        assertEquals(trackID, ordine.getTrackID());
        assertEquals(dataOrdine, ordine.getDataOrdine());
        assertEquals(dettagli, ordine.getDettaglioOrdineList());
        assertEquals(totaleOrdine, ordine.getTotaleOrdine());
        assertEquals(statoOrdine, ordine.getStatoOrdine());
    }
}
