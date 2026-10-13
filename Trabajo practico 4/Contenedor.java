
public class Contenedor {

    private int codigoSKU;
    private String descripcion;
    private double pesoToneladas;
    private String paisOrigen;

    public Contenedor(int codigoSKU, String descripcion, double pesoToneladas, String paisOrigen) {
        this.codigoSKU = codigoSKU;
        this.descripcion = descripcion;
        this.pesoToneladas = pesoToneladas;
        this.paisOrigen = paisOrigen;
    }

    public int getCodigoSKU() {
        return codigoSKU;
    }

    public void setCodigoSKU(int codigoSKU) {
        this.codigoSKU = codigoSKU;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPesoToneladas() {
        return pesoToneladas;
    }

    public void setPesoToneladas(double pesoToneladas) {
        this.pesoToneladas = pesoToneladas;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    
    public String toString() {
        return String.format("SKU: %-6d | %-22s | %8.2f tn | %s",
                codigoSKU, descripcion, pesoToneladas, paisOrigen);
    }
}