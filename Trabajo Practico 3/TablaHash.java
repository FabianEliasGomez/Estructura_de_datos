public class TablaHash {

    private int[] tabla;
    private int m;
    private boolean lineal; // true = lineal, false = cuadratica
    private int colisiones;

    public TablaHash(int m, boolean lineal) {
        this.m = m;
        this.lineal = lineal;
        this.tabla = new int[m];
        // -1 significa casillero vacio
        for (int i = 0; i < m; i++) {
            tabla[i] = -1;
        }
    }

    public int getColisiones() {
        return colisiones;
    }

    private int hash(int clave) {
        return clave % m;
    }

    // posicion que toca probar en el intento i
    private int posicion(int h, int i) {
        if (lineal) {
            return (h + i) % m;
        } else {
            return (h + i * i) % m;
        }
    }

    // devuelve la posicion donde quedo, o -1 si no pudo insertarla
    public int insertar(int clave) {
        int h = hash(clave);
        boolean[] probada = new boolean[m];
        String detalle = "";

        for (int i = 0; i < m; i++) {
            int pos = posicion(h, i);
            if (probada[pos]) {
                continue; // en la cuadratica se repiten posiciones
            }
            probada[pos] = true;

            if (tabla[pos] == -1) {
                tabla[pos] = clave;
                System.out.println("K=" + clave + " H=" + h + " " + detalle + "-> queda en " + pos);
                return pos;
            }
            colisiones++;
            detalle = detalle + "[choca en " + pos + " con " + tabla[pos] + "] ";
        }

        System.out.println("K=" + clave + " H=" + h + " " + detalle + "-> NO SE PUDO INSERTAR");
        return -1;
    }

    // busca la clave y muestra cada comparacion
    public int buscar(int clave) {
        int h = hash(clave);
        boolean[] probada = new boolean[m];
        int comparaciones = 0;

        System.out.println("H(" + clave + ") = " + clave + " mod " + m + " = " + h);

        for (int i = 0; i < m; i++) {
            int pos = posicion(h, i);
            if (probada[pos]) {
                continue;
            }
            probada[pos] = true;

            if (tabla[pos] == -1) {
                System.out.println("indice " + pos + " vacio -> no esta");
                break;
            }
            comparaciones++;
            System.out.println("Comparacion " + comparaciones + ": indice " + pos
                    + " tiene " + tabla[pos]);
            if (tabla[pos] == clave) {
                System.out.println("Lo encontre en el indice " + pos);
                System.out.println("Comparaciones: " + comparaciones);
                return pos;
            }
        }

        System.out.println("Comparaciones: " + comparaciones);
        return -1;
    }

    public void mostrar() {
        String indices = "Indice: ";
        String claves = "Clave:  ";
        for (int i = 0; i < m; i++) {
            indices = indices + String.format("%4d", i);
            if (tabla[i] == -1) {
                claves = claves + "   -";
            } else {
                claves = claves + String.format("%4d", tabla[i]);
            }
        }
        System.out.println(indices);
        System.out.println(claves);
    }
}