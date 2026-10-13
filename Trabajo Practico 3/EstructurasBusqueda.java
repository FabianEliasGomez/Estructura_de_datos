public class EstructurasBusqueda {

    // aca voy contando las comparaciones de cada busqueda
    static int comparaciones;

    // nodo de la lista enlazada
    static class Nodo {
        char dato;
        Nodo sig;

        Nodo(char dato) {
            this.dato = dato;
        }
    }

    // nodo del arbol
    static class NodoABB {
        char dato;
        NodoABB izq, der;

        NodoABB(char dato) {
            this.dato = dato;
        }
    }

    // ---------- ARREGLOS ----------

    static int busquedaSecuencial(char[] v, char clave) {
        comparaciones = 0;
        for (int i = 0; i < v.length; i++) {
            comparaciones++;
            if (v[i] == clave) {
                return i;
            }
        }
        return -1;
    }

    static int busquedaBinaria(char[] v, char clave) {
        comparaciones = 0;
        int low = 0;
        int high = v.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparaciones++;
            if (v[mid] == clave) {
                return mid;
            } else if (v[mid] < clave) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // ---------- LISTAS ----------

    // agrega al final (respeta el orden de llegada)
    static Nodo insertarFinal(Nodo cabeza, char c) {
        Nodo nuevo = new Nodo(c);
        if (cabeza == null) {
            return nuevo;
        }
        Nodo aux = cabeza;
        while (aux.sig != null) {
            aux = aux.sig;
        }
        aux.sig = nuevo;
        return cabeza;
    }

    // agrega en su lugar para que quede ordenada por ASCII
    static Nodo insertarOrdenado(Nodo cabeza, char c) {
        Nodo nuevo = new Nodo(c);
        if (cabeza == null || c < cabeza.dato) {
            nuevo.sig = cabeza;
            return nuevo;
        }
        Nodo aux = cabeza;
        while (aux.sig != null && aux.sig.dato < c) {
            aux = aux.sig;
        }
        nuevo.sig = aux.sig;
        aux.sig = nuevo;
        return cabeza;
    }

    static boolean buscarLista(Nodo cabeza, char clave) {
        comparaciones = 0;
        Nodo aux = cabeza;
        while (aux != null) {
            comparaciones++;
            if (aux.dato == clave) {
                return true;
            }
            aux = aux.sig;
        }
        return false;
    }

    // igual que la otra pero corta si ya nos pasamos de la clave
    static boolean buscarListaOrdenada(Nodo cabeza, char clave) {
        comparaciones = 0;
        Nodo aux = cabeza;
        while (aux != null) {
            comparaciones++;
            if (aux.dato == clave) {
                return true;
            }
            if (aux.dato > clave) {
                return false;
            }
            aux = aux.sig;
        }
        return false;
    }

    static String listaToString(Nodo cabeza) {
        String s = "";
        Nodo aux = cabeza;
        while (aux != null) {
            s = s + aux.dato;
            if (aux.sig != null) {
                s = s + " -> ";
            }
            aux = aux.sig;
        }
        return s;
    }

    // ---------- ABB ----------

    static NodoABB insertarABB(NodoABB raiz, char c) {
        if (raiz == null) {
            return new NodoABB(c);
        }
        if (c < raiz.dato) {
            raiz.izq = insertarABB(raiz.izq, c);
        } else if (c > raiz.dato) {
            raiz.der = insertarABB(raiz.der, c);
        }
        return raiz;
    }

    static boolean buscarABB(NodoABB raiz, char clave) {
        comparaciones = 0;
        NodoABB aux = raiz;
        while (aux != null) {
            comparaciones++;
            if (clave == aux.dato) {
                return true;
            }
            if (clave < aux.dato) {
                aux = aux.izq;
            } else {
                aux = aux.der;
            }
        }
        return false;
    }

    static String preorden(NodoABB raiz) {
        if (raiz == null) {
            return "";
        }
        return raiz.dato + " " + preorden(raiz.izq) + preorden(raiz.der);
    }

    // ---------- INTERPOLACION ----------

    static int busquedaInterpolacion(int[] v, int search) {
        int first = 0;
        int last = v.length - 1;
        int paso = 1;
        comparaciones = 0;

        while (first <= last && search >= v[first] && search <= v[last]) {
            int mid = first + ((search - v[first]) * (last - first)) / (v[last] - v[first]);

            System.out.println("Paso " + paso + ": first=" + first + " last=" + last);
            System.out.println("  mid = " + first + " + (" + search + " - " + v[first] + ") * ("
                    + last + " - " + first + ") / (" + v[last] + " - " + v[first] + ") = " + mid);
            paso++;
            comparaciones++;

            if (v[mid] == search) {
                System.out.println("  v[" + mid + "] = " + v[mid] + " -> lo encontre");
                return mid;
            } else if (v[mid] < search) {
                System.out.println("  v[" + mid + "] = " + v[mid] + " es menor, first = " + (mid + 1));
                first = mid + 1;
            } else {
                System.out.println("  v[" + mid + "] = " + v[mid] + " es mayor, last = " + (mid - 1));
                last = mid - 1;
            }
        }

        System.out.println("No esta. Termina con first=" + first + " last=" + last);
        return -1;
    }
}