package storage.gestioneutente;

import storage.Connector.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Utente doRetrieveByUsernamePassword(String username, String password) {
        String query="Select * from utente where username=? and passwordHash=SHA1(?)";


        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                return null;
            }else {
                int id=rs.getInt("id_utente");
                String nome=rs.getString("nome");
                String cognome=rs.getString("cognome");
                String usernameUser=rs.getString("username");
                String email=rs.getString("email");
                String passwordHash=rs.getString("passwordHash");
                int ruolo=rs.getInt("ruolo");
                if(ruolo==1) {
                    //Client
                    Double saldo=rs.getDouble("saldo");
                    String via=rs.getString("via");
                    int num_civ=rs.getInt("num_civico");
                    int cap=rs.getInt("cap");
                    String città=rs.getString("città");
                    String regione=rs.getString("regione");
                    String provincia=rs.getString("provincia");

                    Indirizzo indirizzo= new Indirizzo(via,num_civ,cap,regione,provincia,città);
                    Cliente cliente= new Cliente();
                    cliente.setId(id);
                    cliente.setNome(nome);
                    cliente.setCognome(cognome);
                    cliente.setUsername(usernameUser);
                    cliente.setEmail(email);
                    cliente.setPasswordHash(passwordHash);
                    cliente.setIndirizzo(indirizzo);
                    cliente.setSaldo(saldo);

                    return cliente;


                } else if (ruolo==0) {
                    //Manager
                    Manager manager= new Manager(nome,cognome,usernameUser,email,passwordHash);
                    return manager;
                }

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    public boolean updatePassword(String emailInserita, String nuovaPassword) {
    String query="Update utente set passwordHash=SHA1(?) where email=?";

    try(Connection con = ConPool.getConnection();
        PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, nuovaPassword);
        ps.setString(2, emailInserita);
        int st = ps.executeUpdate();
        if (st > 0) {
            return true;
        }
        else {
            return false;
        }
        } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }


    public boolean existUsername(String username){
        String query="Select 1 from utente where username = ?";

        try(Connection con = ConPool.getConnection();
        PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1,username);
            try(ResultSet rs = ps.executeQuery()){
                boolean exist;
                exist = rs.next();
                return exist;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public boolean existEmail(String email){
        String query="Select 1 from utente where email = ?";

        try(Connection con = ConPool.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1,email);
            try(ResultSet rs = ps.executeQuery()){
                boolean exist;
                exist = rs.next();
                return exist;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
