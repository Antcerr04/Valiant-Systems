package storage.gestioneutente;

import storage.Connector.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Method to save a user in DB
 */
public class UtenteDAO {
    public void doSave(Cliente utente) {
        String query = "insert into utente (nome,cognome,username,email,passwordHash,saldo,via,num_civico,cap,città,provincia,regione) values(?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            Connection connection = ConPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getUsername());
            ps.setString(4, utente.getEmail());
            ps.setString(5, utente.getPasswordHash());
            ps.setDouble(6, utente.getSaldo());
            ps.setString(7, utente.getIndirizzo().getVia());
            ps.setInt(8, utente.getIndirizzo().getNumCiv());
            ps.setInt(9, utente.getIndirizzo().getCap());
            ps.setString(10, utente.getIndirizzo().getCittà());
            ps.setString(11, utente.getIndirizzo().getProvincia());
            ps.setString(12, utente.getIndirizzo().getRegione());

            int st = ps.executeUpdate();
            if (st > 0) {
                System.out.println("Cliente inserito com sucesso");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Method used to search a user with a specification username and password
     * * @param username of the user to search
     *
     * @param password of the user to search
     * @return if user is in DB return the user, else return null
     */
    public Utente doRetrieveByUsernamePassword(String username, String password) {
        String query = "Select * from utente where username=? and passwordHash=SHA1(?)";


        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                int ruolo = rs.getInt("ruolo");
                Utente utente;
                if (ruolo == 1) {//Cliente
                    Cliente cliente = new Cliente();
                    //Prendo l'indirizzo
                    Indirizzo indirizzo = new Indirizzo(
                            rs.getString("via"),
                            rs.getInt("num_civico"),
                            rs.getInt("cap"),
                            rs.getString("regione"),
                            rs.getString("provincia"),
                            rs.getString("città")
                    );
                    cliente.setIndirizzo(indirizzo);
                    cliente.setSaldo(rs.getDouble("saldo"));
                    utente = cliente;
                } else {
                    //Manager
                    utente = new Manager();
                }
                //Campi comuni
                utente.setId(rs.getInt("id_utente"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setUsername(rs.getString("username"));
                utente.setEmail(rs.getString("email"));
                utente.setPasswordHash(rs.getString("passwordHash"));
                return utente;


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Methos used to update password of the user
     *
     * @param emailInserita email of the user
     * @param nuovaPassword new password
     * @return true if update is successful, else return false
     */
    public boolean updatePassword(String emailInserita, String nuovaPassword) {
        String query = "Update utente set passwordHash=SHA1(?) where email=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, nuovaPassword);
            ps.setString(2, emailInserita);
            int st = ps.executeUpdate();
            if (st > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method used to verify if exists a user with a specification username
     *
     * @param username of the user to search
     * @return true if user exist, else return false
     */
    public boolean existUsername(String username) {
        String query = "Select 1 from utente where username = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                boolean exist;
                exist = rs.next();
                return exist;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Method used to verify if exists a user with a specification email
     *
     * @param email to search
     * @return true if user exist, else return false
     */

    public boolean existEmail(String email) {
        String query = "Select 1 from utente where email = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                boolean exist;
                exist = rs.next();
                return exist;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Method used to update a user
     *
     * @param utente    user to update
     * @param indirizzo address to update
     * @return true if update is successful, else return false
     */
    public boolean updateUtente(Utente utente, Indirizzo indirizzo) {
        String query = "Update utente set nome=?,cognome=?,via=?,num_civico=?,cap=?,città=?,provincia=?,regione=? where email=? and username=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, indirizzo.getVia());
            ps.setInt(4, indirizzo.getNumCiv());
            ps.setInt(5, indirizzo.getCap());
            ps.setString(6, indirizzo.getCittà());
            ps.setString(7, indirizzo.getProvincia());
            ps.setString(8, indirizzo.getRegione());
            ps.setString(9, utente.getEmail());
            ps.setString(10, utente.getUsername());

            int st = ps.executeUpdate();
            if (st > 0) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
