package storage;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class to represents the managers of the systen
 */
public class Manager {
    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String password;
    private String passwordHash;

    /**
     * Empty constructor
     */
    public Manager() {
    }

    /**
     *
     * @param nome of the manager
     * @param cognome of the manager
     * @param username of the manager
     * @param email of the manager
     * @param password of the manager
     */
    public Manager(String nome, String cognome, String username, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Construct to create a new Manager used to delete it
     * @param nome of the manager
     * @param cognome of the manager
     * @param username of the manager
     * @param email of the manager
     */
    public Manager(String nome, String cognome, String username, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
    }

    /**
     *
     * @return name of the manager
     */
    public String getNome() {
        return nome;
    }

    /**
     * Set name of the manager
     * @param nome of the manager
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return surname of the manager
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Set surname of the manager
     * @param cognome of the manager
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     *
     * @return username of the manager
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username of the manager
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return email of the manager
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email of the manager
     * @param email of the manager
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set password of the manager
     * @param password clear of the manager
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return password hash of the manager
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Set passwordHash of the manager
     * @param password to hash
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
     * Method used to validate a name of the manager
     * @param nome to validate
     * @return true if nome is valid else return false
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
}
