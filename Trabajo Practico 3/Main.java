import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // cargo los 16 ejemplares
        int[] chips = {1000, 1150, 1300, 1450, 1600, 1750, 1900, 2050,
                2200, 2350, 2500, 2650, 2800, 2950, 3100, 3250};
        char[] zonas = {'M', '8', 'z', '&', 'o', 'A', 'u', '7',
                '3', 'Q', '%', 'E', '@', 'H', 'c', 'I'};
        int[] expedientes = {25, 43, 56, 35, 54, 13, 80, 104,
                17, 29, 61, 88, 99, 41, 73, 98};

        EjemplarPatagonico[] vectorFauna = new EjemplarPatagonico[16];
        for (int i = 0; i < 16; i++) {
            vectorFauna[i] = new EjemplarPatagonico(chips[i], zonas[i], expedientes[i]);
        }

        System.out.println("===== VECTOR FAUNA =====");
        for (int i = 0; i < vectorFauna.length; i++) {
            System.out.println(i + ": " + vectorFauna[i]);
        }

        parteA(vectorFauna);
        parteB(vectorFauna);
        parteC(vectorFauna);
    }

    static void parteA(EjemplarPatagonico[] vectorFauna) {
        System.out.println("\n===== PARTE A =====");

        // saco los codigos de zona del vector
        char[] desordenado = new char[vectorFauna.length];
        for (int i = 0; i < vectorFauna.length; i++) {
            desordenado[i] = vectorFauna[i].getCodigoZona();
        }

        // arreglo ordenado (Arrays.sort ordena por ASCII)
        char[] ordenado = desordenado.clone();
        Arrays.sort(ordenado);

        // listas y arbol
        EstructurasBusqueda.Nodo listaDes = null;
        EstructurasBusqueda.Nodo listaOrd = null;
        EstructurasBusqueda.NodoABB arbol = null;
        for (int i = 0; i < desordenado.length; i++) {
            listaDes = EstructurasBusqueda.insertarFinal(listaDes, desordenado[i]);
            listaOrd = EstructurasBusqueda.insertarOrdenado(listaOrd, desordenado[i]);
            arbol = EstructurasBusqueda.insertarABB(arbol, desordenado[i]);
        }

        System.out.println("Desordenado: " + new String(desordenado));
        System.out.println("Ordenado:    " + new String(ordenado));
        System.out.println("Lista des.:  " + EstructurasBusqueda.listaToString(listaDes));
        System.out.println("Lista ord.:  " + EstructurasBusqueda.listaToString(listaOrd));
        System.out.println("ABB preorden: " + EstructurasBusqueda.preorden(arbol));

        char[] claves = {'M', 'o', 'u', '3', 'E', 'X', 'w'};

        System.out.println("\nComparaciones (X y w no existen):");
        System.out.printf("%-34s", "Estructura / Clave");
        for (int i = 0; i < claves.length; i++) {
            System.out.printf("%6s", "'" + claves[i] + "'");
        }
        System.out.println();

        System.out.printf("%-34s", "Arreglo desordenado (secuencial)");
        for (int i = 0; i < claves.length; i++) {
            EstructurasBusqueda.busquedaSecuencial(desordenado, claves[i]);
            System.out.printf("%6d", EstructurasBusqueda.comparaciones);
        }
        System.out.println();

        System.out.printf("%-34s", "Arreglo ordenado (binaria)");
        for (int i = 0; i < claves.length; i++) {
            EstructurasBusqueda.busquedaBinaria(ordenado, claves[i]);
            System.out.printf("%6d", EstructurasBusqueda.comparaciones);
        }
        System.out.println();

        System.out.printf("%-34s", "Lista desordenada");
        for (int i = 0; i < claves.length; i++) {
            EstructurasBusqueda.buscarLista(listaDes, claves[i]);
            System.out.printf("%6d", EstructurasBusqueda.comparaciones);
        }
        System.out.println();

        System.out.printf("%-34s", "Lista ordenada");
        for (int i = 0; i < claves.length; i++) {
            EstructurasBusqueda.buscarListaOrdenada(listaOrd, claves[i]);
            System.out.printf("%6d", EstructurasBusqueda.comparaciones);
        }
        System.out.println();

        System.out.printf("%-34s", "ABB");
        for (int i = 0; i < claves.length; i++) {
            EstructurasBusqueda.buscarABB(arbol, claves[i]);
            System.out.printf("%6d", EstructurasBusqueda.comparaciones);
        }
        System.out.println();
    }

    static void parteB(EjemplarPatagonico[] vectorFauna) {
        System.out.println("\n===== PARTE B: INTERPOLACION =====");

        int[] chips = new int[vectorFauna.length];
        for (int i = 0; i < vectorFauna.length; i++) {
            chips[i] = vectorFauna[i].getIdChip();
        }

        System.out.println("\nCaso 1: search = 3100");
        int pos = EstructurasBusqueda.busquedaInterpolacion(chips, 3100);
        System.out.println("Posicion: " + pos + " | comparaciones: " + EstructurasBusqueda.comparaciones);

        System.out.println("\nCaso 2: search = 2450");
        pos = EstructurasBusqueda.busquedaInterpolacion(chips, 2450);
        System.out.println("Posicion: " + pos + " | comparaciones: " + EstructurasBusqueda.comparaciones);
    }

    static void parteC(EjemplarPatagonico[] vectorFauna) {
        System.out.println("\n===== PARTE C: HASHING (M = 16) =====");

        TablaHash lineal = new TablaHash(16, true);
        TablaHash cuadratica = new TablaHash(16, false);

        System.out.println("\n--- Prueba lineal ---");
        for (int i = 0; i < vectorFauna.length; i++) {
            lineal.insertar(vectorFauna[i].getIdExpediente());
        }
        System.out.println();
        lineal.mostrar();
        System.out.println("Colisiones: " + lineal.getColisiones());

        System.out.println("\n--- Prueba cuadratica ---");
        for (int i = 0; i < vectorFauna.length; i++) {
            cuadratica.insertar(vectorFauna[i].getIdExpediente());
        }
        System.out.println();
        cuadratica.mostrar();
        System.out.println("Colisiones: " + cuadratica.getColisiones());

        System.out.println("\n--- Buscar K = 41 en la tabla lineal ---");
        lineal.buscar(41);
    }
}