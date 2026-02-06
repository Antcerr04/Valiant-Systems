package storage.gestioneutente;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Represents the user within the System.
 * This class is used as a Bean to manage data of the user
 *
 * @author Antonio Cerrone
 * @version 1.0
 */
public class Utente {
    private int id;
    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String password;
    private String passwordHash;
    private String ruolo;


    /**
     * Empty constructor
     */
    public Utente() {

    }

    /**
     * Complete constructor to initialize a new User
     *
     * @param nome     name of the user
     * @param cognome  surname of the user
     * @param username username of the user
     * @param email    email of the user
     * @param password password of the user
     */
    public Utente(String nome, String cognome, String username, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * @return Unique identifier of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Set the Unique Identifier for the user
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name of the user
     */
    public String getNome() {
        return nome;
    }

    /**
     * Set name of the user
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the surname of the user
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Set the surname of the user
     *
     * @param cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user
     *
     * @param username
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the origin password of the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the origin password of the user
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the encrypted password of the user
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Set the encrypted password of the user
     *
     * @param password
     */
    public void setPasswordHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            this.passwordHash = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @return the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the user
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;


    }

    /**
     * @param nome to validate
     * @return true if nome respects parameters, else return false
     */

    public static boolean validateNome(String nome) {
        String regex = "^[A-Za-zàèéìòùÀÈÉÌÒÙ \\-']{2,30}$";
        return nome != null && nome.matches(regex);
    }

    /**
     * @param cognome to validate
     * @return true if cognome respects parameters, else return false
     */
    public static boolean validateCognome(String cognome) {
        String regex = "^[A-Za-zàèéìòùÀÈÉÌÒÙ \\-']{2,30}$";
        return cognome != null && cognome.matches(regex);
    }

    /**
     * @param username to validate
     * @return true if username respects parameters, else return false
     */
    public static boolean validateUsername(String username) {
        String regex = "^[a-zA-Z0-9._]{3,20}$";
        return username != null && username.matches(regex);
    }

    /**
     * @param email to validate
     * @return true if email respects parameters,else return false
     */
    public static boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,75}$";
        return email != null && email.matches(regex);
    }

    /**
     * @param password to validate
     * @return true if password respects parameters,else return false
     */
    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#\\$%\\^&\\*\\)\\(+=._-].*");
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
