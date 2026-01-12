package storage.gestionecarrello;

import storage.gestioneinventario.Prodotto;
/**
 * Represents the single item ({@link Prodotto}, quantità) contained inside {@link Carrello}
 */
public class CarrelloItem {
    private Prodotto prodotto;
    private int quantita;


    /**
     * @return The product contained in {@link CarrelloItem}
     */
    public Prodotto getProdotto() {
        return prodotto;
    }

    /**
     * @param prodotto  sets the {@link Prodotto} contained in {@link CarrelloItem}
     */
    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }
    /**
     * @return The quantity of a certain {@link Prodotto} requested
     */
    public int getQuantita() {
        return quantita;
    }

    /**
     * @param quantita  sets the quantity of a certain {@link Prodotto} requested
     */
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}

