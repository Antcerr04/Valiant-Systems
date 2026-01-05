package model.Gestione_Utente;

import model.Connector.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
