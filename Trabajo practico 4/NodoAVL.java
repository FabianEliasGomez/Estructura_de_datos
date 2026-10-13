public class NodoAVL {

    Contenedor carga;
    NodoAVL izquierdo;
    NodoAVL derecho;
    int altura;

    public NodoAVL(Contenedor carga) {
        this.carga = carga;
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 1;
    }
}