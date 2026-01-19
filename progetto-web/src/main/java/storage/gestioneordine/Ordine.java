package storage.gestioneordine;

import storage.gestioneutente.Indirizzo;

import java.sql.Timestamp;
import java.util.List;
/**
 * Represents a user's order within the system.
 *
 */
public class Ordine {
    private int id;
    private String nome;
    private String cognome;
    private Indirizzo indirizzo;
    private String trackID;
    private Timestamp dataOrdine;
    private List<DettaglioOrdine> dettaglioOrdineList;
    private double totaleOrdine;
    private int statoOrdine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTrackID() {
        return trackID;
    }

    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

    public Timestamp getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Timestamp dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public List<DettaglioOrdine> getDettaglioOrdineList() {
        return dettaglioOrdineList;
    }

    public void setDettaglioOrdineList(List<DettaglioOrdine> dettaglioOrdineList) {
        this.dettaglioOrdineList = dettaglioOrdineList;
    }

    public double getTotaleOrdine() {
        return totaleOrdine;
    }

    public void setTotaleOrdine(double totaleOrdine) {
        this.totaleOrdine = totaleOrdine;
    }

    public int getStatoOrdine() {
        return statoOrdine;
    }

    public void setStatoOrdine(int statoOrdine) {
        this.statoOrdine = statoOrdine;
    }
}