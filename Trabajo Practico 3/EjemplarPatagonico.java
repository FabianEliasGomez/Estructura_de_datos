public class EjemplarPatagonico {

    private int idChip;
    private char codigoZona;
    private int idExpediente;

    public EjemplarPatagonico(int idChip, char codigoZona, int idExpediente) {
        this.idChip = idChip;
        this.codigoZona = codigoZona;
        this.idExpediente = idExpediente;
    }

    public int getIdChip() {
        return idChip;
    }

    public void setIdChip(int idChip) {
        this.idChip = idChip;
    }

    public char getCodigoZona() {
        return codigoZona;
    }

    public void setCodigoZona(char codigoZona) {
        this.codigoZona = codigoZona;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }

    public String toString() {
        return "idChip=" + idChip + ", zona=" + codigoZona + ", expediente=" + idExpediente;
    }
}