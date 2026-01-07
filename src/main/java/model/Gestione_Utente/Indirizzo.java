package model.Gestione_Utente;

/**
 * Represents a user's physical address within the system.
 * This class is used as a Bean to manage data relating to geographic location
 *
 * @author Antonio Cerrone
 * @version 1.0
 */

public class Indirizzo {
    private String via;
    private int numCiv;
    private int cap;
    private String regione;
    private String provincia;
    private String città;

    /**
     * Empty constructor
     */
    public Indirizzo() {

    }

    /***
     * Complete Constructor to initialize a new address with all parameters.
     * @param via Name of the street or of the square
     * @param numCiv civic number of the home
     * @param cap postal code
     * @param regione region
     * @param provincia province
     * @param città name of the city
     */
    public Indirizzo(String via, int numCiv, int cap, String regione, String provincia, String città) {
        this.via = via;
        this.numCiv = numCiv;
        this.cap = cap;
        this.regione = regione;
        this.provincia = provincia;
        this.città = città;
    }

    /***
     *
     * @return Name of the street or name of the square
     */

    public String getVia() {
        return via;
    }

    /**
     * Set the name of the street or the name of square
     *
     * @param via
     */

    public void setVia(String via) {
        this.via = via;
    }

    /***
     *
     * @return The civic number
     */

    public int getNumCiv() {
        return numCiv;
    }

    /***
     * Set the civic number.
     * @param numCiv
     */

    public void setNumCiv(int numCiv) {
        this.numCiv = numCiv;
    }

    /***
     *
     * @return Postal Code
     */

    public int getCap() {
        return cap;
    }

    /***
     * Set postal code
     * @param cap
     */
    public void setCap(int cap) {
        this.cap = cap;
    }

    /**
     * @return region
     */

    public String getRegione() {
        return regione;
    }

    /**
     * Set region
     *
     * @param regione
     */

    public void setRegione(String regione) {
        this.regione = regione;
    }

    /**
     * @return province
     */

    public String getProvincia() {
        return provincia;
    }

    /**
     * Set province
     *
     * @param provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * @return Name of the city
     */
    public String getCittà() {
        return città;
    }

    /**
     * Set city's name
     *
     * @param città
     */

    public void setCittà(String città) {
        this.città = città;
    }
}
