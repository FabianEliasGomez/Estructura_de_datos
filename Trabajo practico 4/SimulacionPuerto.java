import java.util.Scanner;

/**
 * Módulo de prueba y simulación del puerto con árbol AVL.
 *  - Módulo 1: simulación automatizada por lote.
 *  - Módulo 2: ingreso interactivo por teclado.
 */
public class SimulacionPuerto {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=========== SIMULACIÓN DEL PUERTO (AVL) ===========");
            System.out.println("1. Módulo 1 - Simulación automatizada por lote");
            System.out.println("2. Módulo 2 - Ingreso interactivo por teclado");
            System.out.println("0. Salir");
            opcion = leerEntero(sc, "Opción: ");
            switch (opcion) {
                case 1:
                    moduloSimulacionLote();
                    break;
                case 2:
                    moduloInteractivo(sc);
                    break;
                case 0:
                    System.out.println("Fin del programa.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
        sc.close();
    }


    static void moduloSimulacionLote() {
        ArbolAVL arbol = new ArbolAVL();

        System.out.println("\n--- MÓDULO 1: SIMULACIÓN POR LOTE ---");

     
        Contenedor[] lote = {
            new Contenedor(50, "Electrónica",        12.5, "China"),
            new Contenedor(30, "Autopartes",         25.0, "Brasil"),
            new Contenedor(70, "Textiles",           18.3, "India"),
            new Contenedor(20, "Maquinaria pesada",  40.0, "Alemania"),
            new Contenedor(40, "Alimentos",          22.0, "Brasil"),
            new Contenedor(60, "Juguetes",            9.8, "China"),
            new Contenedor(80, "Químicos",           35.5, "Alemania"),
            new Contenedor(10, "Calzado",            14.2, "Brasil")
        };
        for (Contenedor c : lote) {
            arbol.insertar(c);
        }
        System.out.println("\n[Etapa 0] Carga de 8 contenedores. Inorden (altura del árbol = " + arbol.altura() + "):");
        arbol.inorden();
        System.out.println("   Estructura:");
        arbol.mostrarEstructura();

        // baja por país
        String pais = "Brasil";
        int n1 = arbol.eliminarPorPais(pais);
        System.out.println("\n[Etapa 1] Baja por país '" + pais + "': " + n1 + " contenedores eliminados. Inorden (altura = " + arbol.altura() + "):");
        arbol.inorden();
        System.out.println("   Estructura:");
        arbol.mostrarEstructura();

        // baja por SKU
        int sku = 50;
        boolean ok = arbol.eliminar(sku);
        System.out.println("\n[Etapa 2] Baja por SKU " + sku + ": " + (ok ? "eliminado" : "no encontrado") + ". Inorden (altura = " + arbol.altura() + "):");
        arbol.inorden();
        System.out.println("   Estructura:");
        arbol.mostrarEstructura();

        // baja por peso 
        double limite = 30.0;
        int n3 = arbol.eliminarSuperanPeso(limite);
        System.out.println("\n[Etapa 3] Baja de contenedores con peso > " + limite + " tn: " + n3 + " eliminados. Inorden (altura = " + arbol.altura() + "):");
        arbol.inorden();
        System.out.println("   Estructura:");
        arbol.mostrarEstructura();
    }

    
    static void moduloInteractivo(Scanner sc) {
        ArbolAVL arbol = new ArbolAVL();
        int opcion;
        do {
            System.out.println("\n--- MÓDULO 2: MENÚ INTERACTIVO ---");
            System.out.println("1. Ingresar nuevo contenedor");
            System.out.println("2. Consultar árbol (inorden)");
            System.out.println("3. Ver estructura del árbol");
            System.out.println("4. Buscar contenedor por SKU");
            System.out.println("5. Eliminar por SKU");
            System.out.println("6. Eliminar por país de origen");
            System.out.println("7. Eliminar contenedores que superan un peso");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero(sc, "Opción: ");

            switch (opcion) {
                case 1: {
                    int sku = leerEntero(sc, "SKU (entero): ");
                    System.out.print("Descripción: ");
                    String desc = sc.nextLine().trim();
                    double peso = leerDouble(sc, "Peso (toneladas): ");
                    System.out.print("País de origen: ");
                    String pais = sc.nextLine().trim();
                    boolean insertado = arbol.insertar(new Contenedor(sku, desc, peso, pais));
                    System.out.println(insertado ? "Contenedor ingresado." : "Ya existe un contenedor con ese SKU.");
                    break;
                }
                case 2:
                    System.out.println("Contenedores (inorden por SKU):");
                    arbol.inorden();
                    break;
                case 3:
                    System.out.println("Estructura (altura del árbol = " + arbol.altura() + "):");
                    arbol.mostrarEstructura();
                    break;
                case 4: {
                    int sku = leerEntero(sc, "SKU a buscar: ");
                    Contenedor c = arbol.buscar(sku);
                    System.out.println(c != null ? c.toString() : "No encontrado.");
                    break;
                }
                case 5: {
                    int sku = leerEntero(sc, "SKU a eliminar: ");
                    System.out.println(arbol.eliminar(sku) ? "Contenedor eliminado." : "SKU no encontrado.");
                    break;
                }
                case 6: {
                    System.out.print("País a dar de baja: ");
                    String pais = sc.nextLine().trim();
                    int n = arbol.eliminarPorPais(pais);
                    System.out.println(n + " contenedor(es) eliminado(s).");
                    break;
                }
                case 7: {
                    double limite = leerDouble(sc, "Peso límite (se eliminan los de peso mayor): ");
                    int n = arbol.eliminarSuperanPeso(limite);
                    System.out.println(n + " contenedor(es) eliminado(s).");
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    
    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, ingresá un número entero.");
            }
        }
    }

    private static double leerDouble(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, ingresá un número (ej: 12.5).");
            }
        }
    }
}