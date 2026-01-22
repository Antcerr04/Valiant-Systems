package storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDAO {
    public boolean insertManager(Manager manager) {
        String query = "insert into utente (nome,cognome,username,email,passwordHash,ruolo) values(?,?,?,?,?,?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, manager.getNome());
            ps.setString(2, manager.getCognome());
            ps.setString(3, manager.getUsername());
            ps.setString(4, manager.getEmail());
            ps.setString(5, manager.getPasswordHash());
            ps.setInt(6,0);

            int rs=ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
            else return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsUsername(String username) {
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

    public boolean existsEmail(String email) {
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

    public boolean isManager(String email) {
        String query = "Select 1 from utente where email = ? and ruolo = ?";

        try(Connection con = ConPool.getConnection();
        PreparedStatement ps= con.prepareStatement(query);) {
            ps.setString(1, email);
            ps.setInt(2, 0);
            try (ResultSet rs = ps.executeQuery()) {
                boolean exist;
                exist = rs.next();
                return exist;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteManager(String email) {
        if (existsEmail(email)) {
            String query = "Delete from utente where email = ? and ruolo = ?";

            try (Connection con = ConPool.getConnection();
                 PreparedStatement ps = con.prepareStatement(query);) {

                ps.setString(1, email);
                ps.setInt(2, 0);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
        else {
            System.out.println("Account non trovato");
            return false;
        }
    }


}
