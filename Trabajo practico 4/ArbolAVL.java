import java.util.ArrayList;
import java.util.List;

public class ArbolAVL {

    private NodoAVL raiz;
    private boolean crecio, decrecio, encontrado; 

    public ArbolAVL() {
        raiz = null;
        crecio = false;
        decrecio = false;
        encontrado = false;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    
    private int altura(NodoAVL n) {
        if (n == null) {
            return 0;
        }
        NodoAVL masBajo = (n.balance > 0) ? n.izquierdo : n.derecho;
        return 1 + Math.abs(n.balance) + altura(masBajo);
    }

    private int factorEquilibrio(NodoAVL n) {
        return (n == null) ? 0 : n.balance;
    }

    private void rotarDerecha(NodoAVL n) {
        NodoAVL m = n.izquierdo;
        Contenedor cc = n.carga;
        n.carga = m.carga;
        m.carga = cc;
        n.izquierdo = m.izquierdo;
        m.izquierdo = m.derecho;
        m.derecho = n.derecho;
        n.derecho = m;
        int bm = 1 + Math.max(-m.balance, 0) + n.balance;
        int bn = 1 + m.balance + Math.max(0, bm);
        n.balance = bn;
        m.balance = bm;
    }

    private void rotarIzquierda(NodoAVL n) {
        NodoAVL m = n.derecho;
        Contenedor cc = n.carga;
        n.carga = m.carga;
        m.carga = cc;
        n.derecho = m.derecho;
        m.derecho = m.izquierdo;
        m.izquierdo = n.izquierdo;
        n.izquierdo = m;
        int bm = -(1 + Math.max(m.balance, 0) - n.balance);
        int bn = -(1 - m.balance + Math.max(0, -bm));
        n.balance = bn;
        m.balance = bm;
    }

    private void errorBalance(NodoAVL n) {
        System.out.println("Error en el valor de equilibrio: " + n.balance
                + " y SKU: " + n.carga.getCodigoSKU());
    }


    public Contenedor buscar(int codigoSKU) {
        NodoAVL n = raiz;
        while (n != null) {
            int k = n.carga.getCodigoSKU();
            if (codigoSKU == k) {
                return n.carga;
            }
            n = (codigoSKU < k) ? n.izquierdo : n.derecho;
        }
        return null;
    }


    public boolean insertar(Contenedor c) {
        encontrado = false;
        crecio = true; 
        raiz = insertar(raiz, c);
        return !encontrado;
    }

    private NodoAVL insertar(NodoAVL n, Contenedor c) {
        if (n == null) {
            return new NodoAVL(c);
        }
        int sku = c.getCodigoSKU();
        if (sku == n.carga.getCodigoSKU()) {
            encontrado = true;
            crecio = false;
        } else {
            if (sku < n.carga.getCodigoSKU()) {
                n.izquierdo = insertar(n.izquierdo, c);
                if (crecio) {
                    n.balance--;
                }
            } else {
                n.derecho = insertar(n.derecho, c);
                if (crecio) {
                    n.balance++; 
                }
            }
            switch (n.balance) {
                case -2:
                    if (n.izquierdo.balance == +1) {
                        rotarIzquierda(n.izquierdo);
                    }
                    rotarDerecha(n);
                    crecio = false;
                    break;
                case -1:
                    break;
                case 0:
                    crecio = false;
                    break;
                case +1:
                    break;
                case +2:
                    if (n.derecho.balance == -1) {
                        rotarDerecha(n.derecho);
                    }
                    rotarIzquierda(n);
                    crecio = false;
                    break;
                default:
                    errorBalance(n);
                    break;
            }
        }
        return n;
    }

    public boolean eliminar(int codigoSKU) {
        encontrado = true;
        decrecio = true;
        raiz = eliminar(raiz, codigoSKU);
        return encontrado;
    }

    private NodoAVL eliminar(NodoAVL n, int sku) {
        if (n == null) {
            encontrado = false;
            decrecio = false;
            return n;
        }
        if (sku == n.carga.getCodigoSKU()) {
            if (n.izquierdo == null) {
                return n.derecho;
            }
            if (n.derecho == null) {
                return n.izquierdo;
            }
           
            n.carga = minimo(n.derecho);
            sku = n.carga.getCodigoSKU();
        }
        if (sku < n.carga.getCodigoSKU()) {
            n.izquierdo = eliminar(n.izquierdo, sku);
            if (decrecio) {
                n.balance++;
            }
        } else {
            n.derecho = eliminar(n.derecho, sku);
            if (decrecio) {
                n.balance--;
            }
        }
        switch (n.balance) {
            case -2:
                switch (n.izquierdo.balance) {
                    case +1:
                        rotarIzquierda(n.izquierdo);
                        break;
                    case 0:
                        decrecio = false;
                        break;
                    case -1:
                        break;
                    default:
                        errorBalance(n.izquierdo);
                        break;
                }
                rotarDerecha(n);
                break;
            case -1:
                decrecio = false;
                break;
            case 0:
                break;
            case +1:
                decrecio = false;
                break;
            case +2:
                switch (n.derecho.balance) {
                    case -1:
                        rotarDerecha(n.derecho);
                        break;
                    case 0:
                        decrecio = false;
                        break;
                    case +1:
                        break;
                    default:
                        errorBalance(n.derecho);
                        break;
                }
                rotarIzquierda(n);
                break;
            default:
                errorBalance(n);
                break;
        }
        return n;
    }

    
    private Contenedor minimo(NodoAVL n) {
        while (n.izquierdo != null) {
            n = n.izquierdo;
        }
        return n.carga;
    }

    
    public int eliminarPorPais(String paisOrigen) {
        List<Contenedor> aEliminar = new ArrayList<>();
        for (Contenedor c : listar()) {
            if (c.getPaisOrigen().equalsIgnoreCase(paisOrigen.trim())) {
                aEliminar.add(c);
            }
        }
        for (Contenedor c : aEliminar) {
            eliminar(c.getCodigoSKU());
        }
        return aEliminar.size();
    }

    
    public int eliminarSuperanPeso(double pesoLimite) {
        List<Contenedor> aEliminar = new ArrayList<>();
        for (Contenedor c : listar()) {
            if (c.getPesoToneladas() > pesoLimite) {
                aEliminar.add(c);
            }
        }
        for (Contenedor c : aEliminar) {
            eliminar(c.getCodigoSKU());
        }
        return aEliminar.size();
    }

    
    
    public List<Contenedor> listar() {
        List<Contenedor> lista = new ArrayList<>();
        listar(raiz, lista);
        return lista;
    }

    private void listar(NodoAVL n, List<Contenedor> lista) {
        if (n == null) {
            return;
        }
        listar(n.izquierdo, lista);
        lista.add(n.carga);
        listar(n.derecho, lista);
    }

  
    public void inorden() {
        if (raiz == null) {
            System.out.println("   (árbol vacío)");
            return;
        }
        for (Contenedor c : listar()) {
            System.out.println("   " + c);
        }
    }

  
    public int altura() {
        return altura(raiz);
    }

    
    public void mostrarEstructura() {
        if (raiz == null) {
            System.out.println("   (árbol vacío)");
            return;
        }
        mostrarEstructura(raiz, 0);
    }

    private void mostrarEstructura(NodoAVL n, int nivel) {
        if (n == null) {
            return;
        }
        mostrarEstructura(n.derecho, nivel + 1);
        System.out.println("   " + "      ".repeat(nivel) + n.carga.getCodigoSKU()
                + " (h=" + altura(n) + ", FE=" + factorEquilibrio(n) + ")");
        mostrarEstructura(n.izquierdo, nivel + 1);
    }

   

  
    public boolean esAVLValido() {
        return alturaVerificada() >= 0;
    }

   
    int alturaVerificada() {
        return verificar(raiz);
    }

    private int verificar(NodoAVL n) {
        if (n == null) {
            return 0;
        }
        int hi = verificar(n.izquierdo);
        int hd = verificar(n.derecho);
        if (hi < 0 || hd < 0) {
            return -1;
        }
        if (hd - hi != n.balance || Math.abs(n.balance) > 1) {
            return -1;
        }
        return 1 + Math.max(hi, hd);
    }
}