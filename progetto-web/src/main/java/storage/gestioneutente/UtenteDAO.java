package storage.gestioneutente;

import storage.Connector.ConPool;

import java.sql.*;

/**
 * Method to save a user in DB
 */
public class UtenteDAO {
    /**
     * Method used to get role of the user
     * @param email of the user
     * @return manager if the role is 0, cliente if role is 1
     */
    public static String getRole(String email) {
        String role = null;
        String query="Select ruolo from utente where email = ?";
        try(Connection con= ConPool.getConnection();
        PreparedStatement ps1=con.prepareStatement(query)){
            ps1.setString(1,email);

            ResultSet rs=ps1.executeQuery();
            if (rs.next()){
                int ruolo=rs.getInt("ruolo");
                if (ruolo==1){
                    role="cliente";
                }
                else if (ruolo==0){
                    role="manager";
                }
                return role;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return role;
    }

    /**
     * Method used to save user
     * @param utente user to save
     */
    public void doSave(Utente utente) {
        String queryIndirizzo = "INSERT INTO indirizzo(via, num_civico, cap, città, provincia, regione) VALUES (?,?,?,?,?,?)";
        String queryUtente = "INSERT INTO utente (nome, cognome, username, email, passwordHash, saldo, id_indirizzo) VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = ConPool.getConnection()) {
            connection.setAutoCommit(false);

            Integer indirizzoId = null;
            Indirizzo indirizzo = utente.getIndirizzo();

            // 1. Insert address (only if exists)
            if (indirizzo != null) {
                try (PreparedStatement ps = connection.prepareStatement(queryIndirizzo, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, indirizzo.getVia());
                    ps.setInt(2, indirizzo.getNumCiv());
                    ps.setInt(3, indirizzo.getCap());
                    ps.setString(4, indirizzo.getCittà());
                    ps.setString(5, indirizzo.getProvincia());
                    ps.setString(6, indirizzo.getRegione());

                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            indirizzoId = rs.getInt(1);
                        }
                    }
                }
            }

            // 2. Insert user
            try (PreparedStatement ps2 = connection.prepareStatement(queryUtente)) {
                ps2.setString(1, utente.getNome());
                ps2.setString(2, utente.getCognome());
                ps2.setString(3, utente.getUsername());
                ps2.setString(4, utente.getEmail());
                ps2.setString(5, utente.getPasswordHash());
                ps2.setDouble(6, utente.getSaldo());

                // Gestione del FK null (per i Manager)
                if (indirizzoId != null) {
                    ps2.setInt(7, indirizzoId);
                } else {
                    ps2.setNull(7, java.sql.Types.INTEGER);
                }

                ps2.executeUpdate();
            }

            connection.commit();
            System.out.println("Salvataggio completato con successo");

        } catch (SQLException e) {

            System.err.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    /**
     * Method used to search a user with a specification username and password
     *  @param username of the user to search
     * @param password of the user to search
     * @return if user is in DB return the user, else return null
     */
    public Utente doRetrieveByUsernamePassword(String username, String password) {

        String query = "SELECT u.*, i.via, i.num_civico, i.cap, i.regione, i.provincia, i.città " +
                "FROM utente u " +
                "LEFT JOIN indirizzo i ON u.id_indirizzo = i.id_indirizzo " +
                "WHERE u.username = ? AND u.passwordHash = SHA1(?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null; // User not exist
                }

                Utente utente = new Utente();

                // Comun field
                utente.setId(rs.getInt("id_utente"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setUsername(rs.getString("username"));
                utente.setEmail(rs.getString("email"));
                utente.setPasswordHash(rs.getString("passwordHash"));

                int ruolo = rs.getInt("ruolo");
                if (ruolo == 1) {
                    utente.setRuolo("cliente");
                    utente.setSaldo(rs.getDouble("saldo"));

                    // Get address if id_indirizzo isn't null
                    //
                    if (rs.getObject("via") != null) {
                        Indirizzo indirizzo = new Indirizzo(
                                rs.getString("via"),
                                rs.getInt("num_civico"),
                                rs.getInt("cap"),
                                rs.getString("regione"),
                                rs.getString("provincia"),
                                rs.getString("città")
                        );
                        utente.setIndirizzo(indirizzo);
                    }
                } else {
                    utente.setRuolo("manager");
                }

                return utente;
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca: " + e.getMessage());
            return null;
        }
    }

    /**
     * Method used to update password of the user
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
     * @param utente    user to update
     * @param indirizzo address to update
     * @return true if update is successful, else return false
     */

    public boolean updateUtente(Utente utente, Indirizzo indirizzo) {
        String queryId = "SELECT id_indirizzo FROM utente WHERE email = ?";
        String queryUtente = "UPDATE utente SET nome=?, cognome=? WHERE email=?";
        String queryIndirizzo = "UPDATE indirizzo SET via=?, num_civico=?, cap=?, città=?, provincia=?, regione=? WHERE id_indirizzo=?";

        Connection con = null;
        try {
            con = ConPool.getConnection();
            con.setAutoCommit(false);

            Integer idIndirizzo = null;

            //Get address id
            try (PreparedStatement ps1 = con.prepareStatement(queryId)) {
                ps1.setString(1, utente.getEmail());
                try (ResultSet rs = ps1.executeQuery()) {
                    if (rs.next()) {
                        idIndirizzo = rs.getInt("id_indirizzo");
                        if (rs.wasNull()) idIndirizzo = null;
                    }
                }
            }

            //Update Address (only if exists)
            if (idIndirizzo != null && indirizzo != null) {
                try (PreparedStatement ps3 = con.prepareStatement(queryIndirizzo)) {
                    ps3.setString(1, indirizzo.getVia());
                    ps3.setInt(2, indirizzo.getNumCiv());
                    ps3.setInt(3, indirizzo.getCap());
                    ps3.setString(4, indirizzo.getCittà());
                    ps3.setString(5, indirizzo.getProvincia());
                    ps3.setString(6, indirizzo.getRegione());
                    ps3.setInt(7, idIndirizzo);
                    ps3.executeUpdate(); // CORRETTO: executeUpdate
                }
            }

            // Update User
            try (PreparedStatement ps2 = con.prepareStatement(queryUtente)) {
                ps2.setString(1, utente.getNome());
                ps2.setString(2, utente.getCognome());
                ps2.setString(3, utente.getEmail());

                int rowsUpdated = ps2.executeUpdate();

                if (rowsUpdated > 0) {
                    con.commit();
                    return true;
                }
            }

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
