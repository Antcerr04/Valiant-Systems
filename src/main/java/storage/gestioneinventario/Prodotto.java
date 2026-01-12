package storage.gestioneinventario;

public class Prodotto {
    private int id;
    private String nome;
    private String immagine;
    private double prezzo;
    private int percSaldo;
    private int quantita;
    private String CPU;
    private String GPU;
    private int SSD_size;
    private int RAM_speed;
    private int RAM_size;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImmagine() { return immagine; }

    public void setImmagine(String immagine) { this.immagine = immagine; }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getPercSaldo() {
        return percSaldo;
    }

    public void setPercSaldo(int percSaldo) {
        this.percSaldo = percSaldo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public String getGPU() {
        return GPU;
    }

    public void setGPU(String GPU) {
        this.GPU = GPU;
    }

    public int getSSD_size() {
        return SSD_size;
    }

    public void setSSD_size(int SSD_size) {
        this.SSD_size = SSD_size;
    }

    public int getRAM_speed() {
        return RAM_speed;
    }

    public void setRAM_speed(int RAM_speed) {
        this.RAM_speed = RAM_speed;
    }

    public int getRAM_size() {
        return RAM_size;
    }

    public void setRAM_size(int RAM_size) {
        this.RAM_size = RAM_size;
    }

    public double getPrezzoSaldo(){ return (Math.round((prezzo - ((this.getPercSaldo() * this.getPrezzo())/100)) * 100) / 100.0); }

}