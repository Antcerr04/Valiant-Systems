package model.Gestione_Utente;

/**
 * This class represent the managers of the system that manage the system
 */
public class Manager extends Utente {

    /**
     * Empty constructor
     */
    public Manager() {
        super();
    }

    /**
     * Complete constructor to create a new manager
     *
     * @param nome     name of the manager
     * @param cognome  suername of the manager
     * @param username username of the manager
     * @param email    email of the manager
     * @param password origin password of the manager
     */
    public Manager(String nome, String cognome, String username, String email, String password) {
        super(nome, cognome, username, password, email);
    }

}
