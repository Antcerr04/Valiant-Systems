package storage.gestioneinventario;

/**
 * This class represent a product
 */
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

    /**
     *
     * @return id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Method to set id of the product
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return name of the product
     */
    public String getNome() {
        return nome;
    }

    /**
     * Method to set a name of the product
     * @param nome of the product to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return image of the product
     */
    public String getImmagine() { return immagine; }

    /**
     * Set the image of the product
     * @param immagine to set
     */
    public void setImmagine(String immagine) { this.immagine = immagine; }

    /**
     *
     * @return price of the product
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * Method to set price of the product
     * @param prezzo to set
     */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    /**
     *
     * @return percentuale saldo of the product
     */
    public int getPercSaldo() {
        return percSaldo;
    }

    /**
     * Method to set a percentuale saldo of the product
     * @param percSaldo to set
     */
    public void setPercSaldo(int percSaldo) {
        this.percSaldo = percSaldo;
    }

    /**
     *
     * @return quantity of the product
     */
    public int getQuantita() {
        return quantita;
    }

    /**
     * Method to set a quantity of the product
     * @param quantita to set
     */
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    /**
     *
     * @return cpu of the product
     */
    public String getCPU() {
        return CPU;
    }

    /**
     * Method to set a cpu of the product
     * @param CPU to set
     */
    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    /**
     *
     * @return gpu of the product
     */
    public String getGPU() {
        return GPU;
    }

    /**
     * Method to set a gpu of the product
     * @param GPU to set
     */
    public void setGPU(String GPU) {
        this.GPU = GPU;
    }

    /**
     *
     * @return ssd size of the product
     */
    public int getSSD_size() {
        return SSD_size;
    }

    /**
     * Method to set SDD size of the product
     * @param SSD_size to set
     */
    public void setSSD_size(int SSD_size) {
        this.SSD_size = SSD_size;
    }

    /**
     *
     * @return ram speed of the product
     */
    public int getRAM_speed() {
        return RAM_speed;
    }

    /**
     * Method to set ram speed of the product
     * @param RAM_speed to set
     */
    public void setRAM_speed(int RAM_speed) {
        this.RAM_speed = RAM_speed;
    }

    /**
     *
     * @return RAM_size of the product
     */
    public int getRAM_size() {
        return RAM_size;
    }

    /**
     * Method to set a RAM size of the product
     * @param RAM_size to set
     */
    public void setRAM_size(int RAM_size) {
        this.RAM_size = RAM_size;
    }

    /**
     *
     * @return percentuale saldo of the product
     */
    public double getPrezzoSaldo(){ return (Math.round((prezzo - ((this.getPercSaldo() * this.getPrezzo())/100)) * 100) / 100.0); }

    //Validation

    /**
     * Method used to validate name of the product
     * @param nome to validate
     * @return true if name is valid else return false
     */
    public static boolean validateNome(String nome){
        return nome != null && nome.trim().length()>=7 && nome.trim().length()<=50;
    }

    /**
     * Method used to validate a price of the product
     * @param prezzo to validate
     * @return true if price is valid else return false
     */
    public static boolean validatePrezzo(double prezzo){
        return prezzo >=100.0 && prezzo <=9999999.99;
    }

    /**
     * Method used to validate percentuale saldo of the product
     * @param percSaldo to validate
     * @return true if percentuale saldo is valid else return false
     */

    public static boolean validatePercSaldo(int percSaldo){
        return percSaldo >=0 && percSaldo <=99;
    }

    /**
     * Method used to validate quantity of the product
     * @param quantita to validate
     * @return true if product is valid else return false
     */
    public static boolean validateQuantita(int quantita){
        return quantita >=1;
    }

    /**
     * Method used to validate a cpu of the product
     * @param cpu to valodate
     * @return true if cpu is valid else return false
     */
    public static boolean validateCPU(String cpu){
        return cpu != null && cpu.trim().length()>=3 && cpu.trim().length()<=60;
    }

    /**
     * Method used to validate a gpu of the product
     * @param gpu to validate
     * @return true if gpu is valid else return false
     */
    public static boolean validateGPU(String gpu){
        return gpu != null && gpu.trim().length()>=3 && gpu.trim().length()<=60;
    }

    /**
     * Method used to validate a ram size of product
     * @param ramSize to validate
     * @return true if ram size is valid else return false
     */

    public static boolean validateRamSize(int ramSize){
        return ramSize >= 4 && (ramSize % 2 ==0);
    }

    /**
     * Method used to validate ram speed of the product
     * @param ramSpeed to validate
     * @return true if ram size is valid else return false
     */

    public static boolean validateRamSpeed(int ramSpeed){
        return ramSpeed >= 1000 && ramSpeed <= 10000;
    }

    /**
     * Method used to validate sdd size of the product
     * @param ssdSize to validate
     * @return true if ssd size is valid else return false
     */

    public static boolean validateSSDSize(int ssdSize){
        return ssdSize >= 256 && (ssdSize % 4 ==0);
    }

}