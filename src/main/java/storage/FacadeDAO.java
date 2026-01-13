package storage;

import storage.gestioneutente.Cliente;
import storage.gestioneutente.Indirizzo;
import storage.gestioneutente.Utente;
import storage.gestioneutente.UtenteDAO;
import storage.gestionecarrello.Carrello;
import storage.gestioneinventario.Prodotto;
import storage.gestioneinventario.ProdottoDAO;
import storage.gestioneordine.Ordine;
import storage.gestioneordine.OrdineDAO;

import java.util.List;

public class FacadeDAO {
    private ProdottoDAO prodottoDAO;
    private UtenteDAO utenteDAO;
    private OrdineDAO ordineDAO;


    public FacadeDAO() {
        utenteDAO = new UtenteDAO();
        ordineDAO = new OrdineDAO();
        prodottoDAO = new ProdottoDAO();
    }


    /**
     * Returns the list of {@link Prodotto} contained in the database.
     *
     * @return a list of {@link Prodotto} objects
     */
    public List<Prodotto> getAllProducts() {
        return prodottoDAO.doRetrieveAll();
    }

    /**
     * Adds a {@link Prodotto} to the database.
     * @param prodotto the {@link Prodotto} to add to the database
     * @return 1 when the operation is successful.
     */
    public int saveProduct(Prodotto prodotto) {
        return prodottoDAO.doSave(prodotto);
    }

    /**
     * Returns the list of {@link Prodotto} contained in the database that are on sale.
     *
     * @return a list of {@link Prodotto} objects that are on sale.
     */
    public List<Prodotto> getAllProductsOnSale(){
        return prodottoDAO.doRetrieveAllAvailableSales();
    }

    /**
     * Returns the {@link Prodotto} with a specific ID
     * @param id the {@link Prodotto} id to find
     * @return the {@link Prodotto} correlated to the searched id.
     * <p>Returns null if it's not present.</p>
     */
    public Prodotto getProductById(int id) {
        return prodottoDAO.doRetrieveById(id);
    }

    /**
     * Returns the list of {@link Prodotto} that have
     * @param name the name of a {@link Prodotto} to find
     * @return the resulting list of the search.
     * <p>Returns an empty list if the search didn't yield any result.</p>
     */
    public List<Prodotto> searchProduct(String name) {
        return prodottoDAO.doSearchByName(name);
    }

    /**
     * Deletes a {@link Prodotto} with a specific ID
     * @param id the {@link Prodotto} id to delete
     * @return true if the successful, false otherwise.
     */
    public boolean deleteProduct(int id) {
        return prodottoDAO.doDeleteByID(id);
    }

    /**
     * Update the information of the {@link Prodotto} given
     * @param prodotto the new {@link Prodotto} information
     * @return 1 if the successful, 0 otherwise.
     */
    public int updateProduct(Prodotto prodotto) {
        return prodottoDAO.updateProduct(prodotto);
    }

    /**
     * Get the list of distinct CPU contained in the database
     * @return list of String object containing all distinct CPUs
     */
    public List<String> getAllProductCPU(){
        return prodottoDAO.doGetCPUlist();
    }

    /**
     * Get the list of distinct GPU that have the CPU inserted as input
     * @param cpu the parameter to search
     * @return list of String object containing all distinct GPUs with the given CPU
     */
    public List<String> getAllProductGPU(String cpu){
        return prodottoDAO.doGetGPUlist(cpu);
    }

    /**
     * Get the list of {@link Prodotto} that have the given CPU AND GPU
     * @param cpu the name of the cpu to filter
     * @param gpu the name of the gpu to filter
     * @return the list of {@link Prodotto} that have the given CPU AND GPU
     */
    public List<Prodotto> filterProductByCPUGPU(String cpu,String gpu){
        return prodottoDAO.doFilter(cpu,gpu);
    }
    /**
     * Stores a {@link Cliente} in the database.
     * @param cliente the {@link Cliente} to add to the database
     */
    public void saveClient(Cliente cliente) {
        utenteDAO.doSave(cliente);
    }
    /**
     * Retrieves the {@link Utente} data from the database given the username and password.
     * <p>Used for login.</p>
     * @param username the name of the user
     * @param password the password of the user
     * @return {@link Utente} when the operation is successful. null otherwise.
     */
    public Utente getUserByCredentials(String username, String password){
        return utenteDAO.doRetrieveByUsernamePassword(username, password);
    }

    /**
     * Updates  the {@link Utente} password given the email and new password.
     * @param email the email of the user
     * @param newPassword the new password of the user
     * @return true when the operation is successful. false otherwise.
     */
    public boolean updateUserPassword(String email, String newPassword) {
        return utenteDAO.updatePassword(email, newPassword);
    }

    /**
     * Checks if the username given is present in the database
     * @param username the username to check
     * @return true when the operation is successful. false otherwise.
     */
    public boolean isUserPresent(String username) {
        return utenteDAO.existUsername(username);
    }

    /**
     * Checks if the email given is present in the database
     * @param email the email to check
     * @return true when the operation is successful. false otherwise.
     */
    public boolean isEmailPresent(String email) {
        return utenteDAO.existEmail(email);
    }

    /**
     * Gets all the order that a given {@link Cliente} has made.
     * @param cliente the {@Cliente} whose order have to be retrieved.
     * @return a list of {@link Ordine} if there are any, an empty list otherwise.
     */
    public List<Ordine> getAllClientOrders(Cliente cliente){
        return ordineDAO.doRetrieveAll(cliente);
    }

    /**
     * Checkout operation that commits the {@link Cliente}'s order to the database
     * <p>Controls for the availability of the requested products, updates the product's quantity, saves the order and updates the client's balance.</p>
     * @param cliente the {@link Cliente} that has made the checkout operation
     * @param carrello the {@link Cliente}'s shopping cart
     * @return an empty list if successful, a list of errors otherwise.
     */
    public List<String> checkout(Cliente cliente, Carrello carrello) {
        return ordineDAO.doCheckout(carrello, cliente);
    }

    /**
     *
     * @param utente the {@link Utente} to update
     * @param indirizzo the {@link Indirizzo} to update
     * @return true if successful update, else return false
     */
    public boolean updateAccount(Utente utente, Indirizzo indirizzo) { return utenteDAO.updateUtente(utente,indirizzo);}
}
