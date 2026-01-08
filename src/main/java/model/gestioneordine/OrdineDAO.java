package model.gestioneordine;

import model.Connector.ConPool;
import model.Gestione_Utente.Indirizzo;
import model.Gestione_Utente.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {
    public List<Ordine> doRetrieveAll(Utente utente) {
        List<Ordine> ordineList = new ArrayList<Ordine>();

        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ordine WHERE utente = ? ORDER BY data_ordine DESC");
            ps.setInt(1, utente.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double totale = 0;
                Indirizzo indirizzo = new Indirizzo();
                indirizzo.setVia(rs.getString("via"));
                indirizzo.setNumCiv(rs.getInt("num_civico"));
                indirizzo.setCap(rs.getInt("cap"));
                indirizzo.setCittà(rs.getString("citta"));
                indirizzo.setProvincia(rs.getString("provincia"));
                indirizzo.setRegione(rs.getString("regione"));

                Ordine ordine = new Ordine();
                ordine.setId(rs.getInt("id"));
                ordine.setIndirizzo(indirizzo);
                ordine.setTrackID(rs.getString("track_id"));
                ordine.setDataOrdine(rs.getTimestamp("data_ordine"));

                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM dettaglio_ordine WHERE ordine = ?");
                ps2.setInt(1, ordine.getId());
                ResultSet rs2 = ps2.executeQuery();
                List<DettaglioOrdine> dettaglioList = new ArrayList<DettaglioOrdine>();
                while (rs2.next()) {
                    DettaglioOrdine dettaglio = new DettaglioOrdine();
                    if(rs2.getInt("prodotto") == 0)
                        dettaglio.setImmagine("DELETED.png");
                    else
                        dettaglio.setImmagine(rs2.getString("immagine"));

                    dettaglio.setIdProdotto(rs2.getInt("prodotto"));
                    dettaglio.setNomeProdotto(rs2.getString("nome_prod"));
                    dettaglio.setQuantita(rs2.getInt("quantita"));
                    dettaglio.setPrezzoUnitario(rs2.getDouble("prezzo_u"));
                    dettaglioList.add(dettaglio);
                    totale += dettaglio.getTotale();
                }
                ordine.setDettaglioOrdineList(dettaglioList);
                ordine.setTotaleOrdine(totale);
                ordineList.add(ordine);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordineList;
    }
}