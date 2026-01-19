package storage.gestioneinventario;

import storage.Connector.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    public int doSave(Prodotto prodotto){
        String query="insert into prodotto (nome,prezzo,immagine,percSaldo,quantita,cpu,gpu,ram_size,ram_speed,ssd_size) values (?,?,?,?,?,?,?,?,?,?)";

        try{
            Connection connection= ConPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,prodotto.getNome());
            ps.setDouble(2,prodotto.getPrezzo());
            ps.setString(3,prodotto.getImmagine());
            ps.setInt(4,prodotto.getPercSaldo());
            ps.setInt(5,prodotto.getQuantita());
            ps.setString(6,prodotto.getCPU());
            ps.setString(7,prodotto.getGPU());
            ps.setInt(8,prodotto.getRAM_size());
            ps.setInt(9,prodotto.getRAM_speed());
            ps.setInt(10,prodotto.getSSD_size());

            int st = ps.executeUpdate();
            if (st > 0) {
                System.out.println("Prodotto inserito com sucesso");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return 1;
    }




    public List<Prodotto> doRetrieveAll() {
        List<Prodotto> prodotti = new ArrayList<Prodotto>();
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setImmagine(rs.getString("immagine"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setPercSaldo(rs.getInt("percSaldo"));
                prodotto.setQuantita(rs.getInt("quantita"));
                prodotto.setCPU(rs.getString("cpu"));
                prodotto.setGPU(rs.getString("gpu"));
                prodotto.setRAM_size(rs.getInt("ram_size"));
                prodotto.setRAM_speed(rs.getInt("ram_speed"));
                prodotto.setSSD_size(rs.getInt("ssd_size"));

                prodotti.add(prodotto);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotti;
    }

    public List<Prodotto> doRetrieveAllAvailableSales() {
        List<Prodotto> prodotti = new ArrayList<Prodotto>();
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto WHERE quantita>0 AND percSaldo>0");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setImmagine(rs.getString("immagine"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setPercSaldo(rs.getInt("percSaldo"));
                prodotto.setQuantita(rs.getInt("quantita"));
                prodotto.setCPU(rs.getString("cpu"));
                prodotto.setGPU(rs.getString("gpu"));
                prodotto.setRAM_size(rs.getInt("ram_size"));
                prodotto.setRAM_speed(rs.getInt("ram_speed"));
                prodotto.setSSD_size(rs.getInt("ssd_size"));

                prodotti.add(prodotto);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotti;
    }

    public Prodotto doRetrieveById(int id) {
        String query="Select * from prodotto where id=?";
        Prodotto prodotto=new Prodotto();
        try{
            Connection con=ConPool.getConnection();
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs= ps.executeQuery();
            if(!rs.next()){
                return null;
            }else {
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setImmagine(rs.getString("immagine"));
                prodotto.setPercSaldo(rs.getInt("percSaldo"));
                prodotto.setQuantita(rs.getInt("quantita"));
                prodotto.setCPU(rs.getString("cpu"));
                prodotto.setGPU(rs.getString("gpu"));
                prodotto.setRAM_size(rs.getInt("ram_size"));
                prodotto.setRAM_speed(rs.getInt("ram_speed"));
                prodotto.setSSD_size(rs.getInt("ssd_size"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return prodotto;
    }

    public List<Prodotto> doSearchByName(String query) {
        List<Prodotto> prodotti = new ArrayList<Prodotto>();
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto where nome like ?");
            ps.setString(1, "%"+query+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setImmagine(rs.getString("immagine"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setPercSaldo(rs.getInt("percSaldo"));
                prodotto.setQuantita(rs.getInt("quantita"));
                prodotto.setCPU(rs.getString("cpu"));
                prodotto.setGPU(rs.getString("gpu"));
                prodotto.setRAM_size(rs.getInt("ram_size"));
                prodotto.setRAM_speed(rs.getInt("ram_speed"));
                prodotto.setSSD_size(rs.getInt("ssd_size"));

                prodotti.add(prodotto);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prodotti;
    }

    public boolean doDeleteByID(int id) {
        try(Connection con = ConPool.getConnection()){
            String query= "DELETE FROM prodotto WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,id);
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public int updateProduct(Prodotto prodotto) {
        String query = "Update prodotto" +
                " set nome=?, prezzo=?, immagine=?, percSaldo=?, quantita=?, cpu=?, gpu=?, ram_size=?, ram_speed=?,ssd_size=?" +
                " where id=?";
        try{
            Connection con=ConPool.getConnection();
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,prodotto.getNome());
            ps.setDouble(2,prodotto.getPrezzo());
            ps.setString(3,prodotto.getImmagine());
            ps.setInt(4,prodotto.getPercSaldo());
            ps.setInt(5,prodotto.getQuantita());
            ps.setString(6,prodotto.getCPU());
            ps.setString(7,prodotto.getGPU());
            ps.setInt(8,prodotto.getRAM_size());
            ps.setInt(9,prodotto.getRAM_speed());
            ps.setInt(10,prodotto.getSSD_size());
            ps.setInt(11,prodotto.getId());

            int rs = ps.executeUpdate();
            if (rs > 0) {
                return 1;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<String> doGetCPUlist(){
        List<String> cpuList = new ArrayList<>();
        try(Connection con = ConPool.getConnection()) {
            String query = "SELECT DISTINCT cpu FROM prodotto ORDER BY cpu";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cpuList.add(rs.getString("cpu"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  cpuList;
    }

    public List<String> doGetGPUlist(String cpu){
        List<String> gpuList = new ArrayList<>();
        try(Connection con = ConPool.getConnection()) {
            String query = "SELECT DISTINCT gpu FROM prodotto WHERE cpu=? ORDER BY gpu";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,cpu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gpuList.add(rs.getString("gpu"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  gpuList;
    }
    public List<Prodotto> doFilter(String cpu, String gpu){
        List<Prodotto> prodotti = new ArrayList<>();
        try(Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM prodotto WHERE cpu=? AND gpu=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,cpu);
            ps.setString(2,gpu);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setImmagine(rs.getString("immagine"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setPercSaldo(rs.getInt("percSaldo"));
                prodotto.setQuantita(rs.getInt("quantita"));
                prodotto.setCPU(rs.getString("cpu"));
                prodotto.setGPU(rs.getString("gpu"));
                prodotto.setRAM_size(rs.getInt("ram_size"));
                prodotto.setRAM_speed(rs.getInt("ram_speed"));
                prodotto.setSSD_size(rs.getInt("ssd_size"));

                prodotti.add(prodotto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  prodotti;
    }
}
