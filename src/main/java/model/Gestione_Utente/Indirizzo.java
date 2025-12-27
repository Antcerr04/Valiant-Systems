package model.Gestione_Utente;

public class Indirizzo {
    private int id;
    private String via;
    private int numCiv;
    private int cap;
    private String regione;
    private String provincia;
    private String città;

    public Indirizzo() {

    }

    public Indirizzo(int id, String via, int numCiv, int cap, String regione, String provincia, String città) {
        this.id = id;
        this.via = via;
        this.numCiv = numCiv;
        this.cap = cap;
        this.regione = regione;
        this.provincia = provincia;
        this.città = città;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public int getNumCiv() {
        return numCiv;
    }

    public void setNumCiv(int numCiv) {
        this.numCiv = numCiv;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }
}
