package storage.gestioneordine;

import storage.Connector.ConPool;
import storage.gestioneutente.Indirizzo;
import storage.gestioneutente.Cliente;
import storage.gestionecarrello.Carrello;
import storage.gestionecarrello.CarrelloItem;
import storage.gestioneinventario.Prodotto;
import storage.gestioneinventario.ProdottoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {
    public List<Ordine> doRetrieveClientOrders(Cliente cliente) {
        List<Ordine> ordineList = new ArrayList<Ordine>();

        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ordine WHERE utente = ? ORDER BY data_ordine DESC");
            ps.setInt(1, cliente.getId());
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

    public synchronized List<String> doCheckout(Carrello carrello, Cliente cliente) {
        String query="INSERT INTO ordine (utente,via,num_civico,cap,citta,provincia,regione,track_id) VALUES (?,?,?,?,?,?,?,?)";
        int orderID = -1;
        double total = 0;
        List<String> errorList = new ArrayList<>();
        try(Connection connection = ConPool.getConnection()){
            try{
                connection.setAutoCommit(false);
                ProdottoDAO service = new ProdottoDAO();
                List<CarrelloItem> carrelloList = carrello.getCarrelloItemList();
                for(CarrelloItem carrelloItem : carrelloList){
                    total += carrelloItem.getProdotto().getPrezzoSaldo() * carrelloItem.getQuantita();
                    Prodotto currentProdDATA = service.doRetrieveById(carrelloItem.getProdotto().getId());
                    if(currentProdDATA.getQuantita() < carrelloItem.getQuantita())
                        errorList.add ("La quantità richiesta di '"+currentProdDATA.getNome()+"' ("+carrelloItem.getQuantita()+") è inferiore a quella disponibile ("+currentProdDATA.getQuantita()+").");
                    else {
                        String query1 = "UPDATE prodotto SET quantita=? WHERE id=?";
                        PreparedStatement ps1 = connection.prepareStatement(query1);
                        ps1.setInt(1,(currentProdDATA.getQuantita() - carrelloItem.getQuantita()));
                        ps1.setInt(2, carrelloItem.getProdotto().getId());
                        if(ps1.executeUpdate() < 0)
                            errorList.add("Errore aggiornamento della nuova quantità del prodotto: " + carrelloItem.getProdotto().getNome());
                    }
                }
                if(!errorList.isEmpty()){
                    connection.rollback();
                    return errorList;
                }

                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, cliente.getId());
                Indirizzo indirizzo = cliente.getIndirizzo();
                ps.setString(2, indirizzo.getVia());
                ps.setInt(3, indirizzo.getNumCiv());
                ps.setInt(4, indirizzo.getCap());
                ps.setString(5, indirizzo.getCittà());
                ps.setString(6, indirizzo.getProvincia());
                ps.setString(7, indirizzo.getRegione());

                String track = "F123D" + String.format("%6d", (int) (Math.random()*999999)).replace(' ','0');
                ps.setString(8, track);

                if(ps.executeUpdate() < 0) {
                    connection.rollback();
                    errorList.add("Errore inserimento ordine!");
                    return errorList;
                }
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    orderID = rs.getInt(1);
                }
                for (CarrelloItem carrelloItem : carrelloList) {
                    String query2 = "INSERT INTO dettaglio_ordine (ordine,prodotto,quantita,prezzo_u,nome_prod,immagine) VALUES (?,?,?,?,?,?)";
                    PreparedStatement ps2 = connection.prepareStatement(query2);
                    ps2.setInt(1, orderID);
                    ps2.setInt(2, carrelloItem.getProdotto().getId());
                    ps2.setInt(3, carrelloItem.getQuantita());
                    double prezzo = carrelloItem.getProdotto().getPrezzoSaldo();
                    ps2.setDouble(4, prezzo);
                    ps2.setString(5, carrelloItem.getProdotto().getNome());
                    ps2.setString(6, carrelloItem.getProdotto().getImmagine());

                    if(ps2.executeUpdate() < 0) {
                        connection.rollback();
                        errorList.add("Errore inserimento dettaglio ordine.");
                        return errorList;
                    }
                }
                String query3 = "UPDATE utente SET saldo=? WHERE id_utente=?";
                PreparedStatement ps3=connection.prepareStatement(query3);
                ps3.setDouble(1, (Math.round((cliente.getSaldo() - total) * 100) / 100.0));
                ps3.setInt(2, cliente.getId());
                if(ps3.executeUpdate() < 0) {
                    connection.rollback();
                    errorList.add("Errore aggiornamento saldo utente.");
                    return errorList;
                }
                cliente.setSaldo((Math.round((cliente.getSaldo() - total) * 100) / 100.0));
                connection.commit();
            }catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return errorList;
    }
}