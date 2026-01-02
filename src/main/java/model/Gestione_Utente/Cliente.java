package model.Gestione_Utente;

/**
 * This class represents a specialization of the Utente class
 * This class represents the clients that use the system to do orders
 */
public class Cliente extends Utente {
    private Double saldo;
    private Indirizzo indirizzo;

    /**
     * Empty constructor
     */
    public Cliente() {

    }

    /**
     * Complete constructor to create a new client
     *
     * @param nome         name of the client
     * @param cognome      surname of the client
     * @param username     username of the client
     * @param password     password of the client
     * @param email        email of the client
     * @param passwordHash encrypted password of the client
     * @param saldo        balance of the client
     * @param indirizzo    address of the client
     */
    public Cliente(String nome, String cognome, String username, String password, String email, String passwordHash, Double saldo, Indirizzo indirizzo) {
        super(nome, cognome, username, password, email, passwordHash);
        this.saldo = saldo;
        this.indirizzo = indirizzo;
    }

    /**
     * @return balance of the client
     */
    public Double getSaldo() {
        return saldo;
    }

    /**
     * Set the balance of the client
     *
     * @param saldo
     */
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the address of the client
     */
    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    /**
     * Set the address of the client
     *
     * @param indirizzo
     */
    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }
}
